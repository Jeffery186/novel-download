package com.java2nb.novel.mapper;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BookCrawlSourceDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BookCrawlSource bookCrawlSource = new BookCrawlSource();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> id = bookCrawlSource.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> bookId = bookCrawlSource.bookId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> sourceId = bookCrawlSource.sourceId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> sourceName = bookCrawlSource.sourceName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> sourceBookId = bookCrawlSource.sourceBookId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> catId = bookCrawlSource.catId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> bookName = bookCrawlSource.bookName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> authorName = bookCrawlSource.authorName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = bookCrawlSource.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = bookCrawlSource.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BookCrawlSource extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<Long> bookId = column("book_id", JDBCType.BIGINT);

        public final SqlColumn<Integer> sourceId = column("source_id", JDBCType.INTEGER);

        public final SqlColumn<String> sourceName = column("source_name", JDBCType.VARCHAR);

        public final SqlColumn<String> sourceBookId = column("source_book_id", JDBCType.VARCHAR);

        public final SqlColumn<Integer> catId = column("cat_id", JDBCType.INTEGER);

        public final SqlColumn<String> bookName = column("book_name", JDBCType.VARCHAR);

        public final SqlColumn<String> authorName = column("author_name", JDBCType.VARCHAR);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public BookCrawlSource() {
            super("book_crawl_source");
        }
    }
}