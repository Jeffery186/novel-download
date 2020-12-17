package com.java2nb.novel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java2nb.novel.core.bean.ResultBean;
import com.java2nb.novel.core.bean.RuleBean;
import com.java2nb.novel.core.cache.CacheService;
import com.java2nb.novel.core.utils.*;
import com.java2nb.novel.entity.BookContent;
import com.java2nb.novel.entity.BookCrawlSource;
import com.java2nb.novel.entity.BookIndex;
import com.java2nb.novel.entity.CrawlSource;
import com.java2nb.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author 11797
 */
@Controller
@RequestMapping("file")
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final CacheService cacheService;

    private final BookService bookService;


    @Value("${pic.save.path}")
    private String picSavePath;

    /**
     * 生成验证码
     */
    @GetMapping(value = "getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            //输出验证码图片方法
            randomValidateCode.getRandcode(cacheService, response);
        } catch (Exception e) {
            log.error("获取验证码失败>>>> ", e);
        }
    }

    /**
     * 文件上传
     */
    @ResponseBody
    @PostMapping("/upload")
    ResultBean upload(@RequestParam("file") MultipartFile file) {
        Date currentDate = new Date();
        try {
            String savePath =
                    Constants.LOCAL_PIC_PREFIX + DateUtils.formatDate(currentDate, "yyyy") + "/" +
                            DateUtils.formatDate(currentDate, "MM") + "/" +
                            DateUtils.formatDate(currentDate, "dd");
            String oriName = file.getOriginalFilename();
            String saveFileName = UUIDUtil.getUUID32() + oriName.substring(oriName.lastIndexOf("."));
            File saveFile = new File(picSavePath + savePath, saveFileName);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            file.transferTo(saveFile);
            return ResultBean.ok(savePath + "/" + saveFileName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.error();
        }

    }

    /**
     * 查询是否正在下载
     */
    @RequestMapping("queryIsDownloading")
    @ResponseBody
    public Map<String, Object> queryIsDownloading(HttpSession session) {
        Map<String, Object> result = new HashMap<>(1);
        if (session.getAttribute(Constants.NOVEL_IS_DOWNLOADING_KEY) != null) {
            result.put("code", 1);
        } else {
            result.put("code", 0);
        }
        return result;
    }

    /**
     * 文件下载
     */
    @RequestMapping(value = "/bookDownload")
    public void bookDownload(@RequestParam("sourceId") Integer sourceId, @RequestParam("sourceBookId") String sourceBookId, @RequestParam("bookName") String bookName, HttpServletResponse resp, HttpSession session) {
        try {
            OutputStream out = resp.getOutputStream();
            if(session.getAttribute(Constants.NOVEL_IS_DOWNLOADING_KEY) != null){
                resp.setContentType("text/html;charset=utf-8");
                out.write("服务器资源有限，目前不支持多本小说同时下载，请等待当前下载完成后再重试！".getBytes(StandardCharsets.UTF_8));
           return;
            }
            session.setAttribute(Constants.NOVEL_IS_DOWNLOADING_KEY, 1);

            //设置响应头，对文件进行url编码
            bookName = URLEncoder.encode(bookName, "UTF-8");
            //解决手机端不能下载附件的问题
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment;filename=" + bookName + ".txt");

            //查询爬虫源信息
            CrawlSource source = bookService.queryCrawlSource(sourceId);
            RuleBean ruleBean = new ObjectMapper().readValue(source.getCrawlRule(), RuleBean.class);


            //读取目录
            String indexListUrl = ruleBean.getBookIndexUrl().replace("{bookId}", sourceBookId);
            String indexListHtml = HttpUtil.getByHttpClientWithChrome(indexListUrl);

            if (indexListHtml != null) {
                if (StringUtils.isNotBlank(ruleBean.getBookIndexStart())) {
                    indexListHtml = indexListHtml.substring(indexListHtml.indexOf(ruleBean.getBookIndexStart()) + ruleBean.getBookIndexStart().length());
                }

                Pattern indexIdPatten = compile(ruleBean.getIndexIdPatten());
                Matcher indexIdMatch = indexIdPatten.matcher(indexListHtml);

                Pattern indexNamePatten = compile(ruleBean.getIndexNamePatten());
                Matcher indexNameMatch = indexNamePatten.matcher(indexListHtml);

                boolean isFindIndex = indexIdMatch.find() & indexNameMatch.find();



                while (isFindIndex) {

                    String indexName = indexNameMatch.group(1);


                    String sourceIndexId = indexIdMatch.group(1);
                    String bookContentUrl = ruleBean.getBookContentUrl();
                    int calStart = bookContentUrl.indexOf("{cal_");
                    if (calStart != -1) {
                        //内容页URL需要进行计算才能得到
                        String calStr = bookContentUrl.substring(calStart, calStart + bookContentUrl.substring(calStart).indexOf("}"));
                        String[] calArr = calStr.split("_");
                        int calType = Integer.parseInt(calArr[1]);
                        if (calType == 1) {
                            ///{cal_1_1_3}_{bookId}/{indexId}.html
                            //第一种计算规则，去除第x个参数的最后y个字母
                            int x = Integer.parseInt(calArr[2]);
                            int y = Integer.parseInt(calArr[3]);
                            String calResult;
                            if (x == 1) {
                                calResult = sourceBookId.substring(0, sourceBookId.length() - y);
                            } else {
                                calResult = sourceIndexId.substring(0, sourceBookId.length() - y);
                            }

                            if (calResult.length() == 0) {
                                calResult = "0";

                            }

                            bookContentUrl = bookContentUrl.replace(calStr + "}", calResult);
                        }

                    }

                    String contentUrl = bookContentUrl.replace("{bookId}", sourceBookId).replace("{indexId}", sourceIndexId);

                    //查询章节内容
                    String contentHtml = HttpUtil.getByHttpClientWithChrome(contentUrl);
                    if (contentHtml != null && !contentHtml.contains("正在手打中")) {
                        String content = contentHtml.substring(contentHtml.indexOf(ruleBean.getContentStart()) + ruleBean.getContentStart().length());
                        content = content.substring(0, content.indexOf(ruleBean.getContentEnd()));
                        out.write(indexName.getBytes(StandardCharsets.UTF_8));
                        out.write("\n".getBytes(StandardCharsets.UTF_8));
                        content = content.replaceAll(Constants.CONTENT_AD_PATTERN, "")
                                .replaceAll("<br\\s*/*>", "\r\n")
                                .replaceAll("&nbsp;", " ")
                                .replaceAll("<a[^>]*>", "")
                                .replaceAll("</a>", "")
                                .replaceAll("<div[^>]*>", "")
                                .replaceAll("</div>", "")
                                .replaceAll("<p[^>]*>[^<]*<a[^>]*>[^<]*</a>\\s*</p>", "")
                                .replaceAll("<p[^>]*>", "")
                                .replaceAll("</p>", "\r\n");
                        out.write(content.getBytes(StandardCharsets.UTF_8));
                        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
                        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
                        out.flush();


                    }else{
                        break;
                    }
                    isFindIndex = indexIdMatch.find() & indexNameMatch.find();
                }
            }


            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.removeAttribute(Constants.NOVEL_IS_DOWNLOADING_KEY);
        }

    }


}
