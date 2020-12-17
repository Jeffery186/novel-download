package com.java2nb.novel.mapper;

import static com.java2nb.novel.mapper.BookCrawlSourceDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.java2nb.novel.entity.BookCrawlSource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface BookCrawlSourceMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(id, bookId, sourceId, sourceName, sourceBookId, catId, bookName, authorName, createTime, updateTime);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BookCrawlSource> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<BookCrawlSource> multipleInsertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BookCrawlSourceResult")
    Optional<BookCrawlSource> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BookCrawlSourceResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="book_id", property="bookId", jdbcType=JdbcType.BIGINT),
        @Result(column="source_id", property="sourceId", jdbcType=JdbcType.INTEGER),
        @Result(column="source_name", property="sourceName", jdbcType=JdbcType.VARCHAR),
        @Result(column="source_book_id", property="sourceBookId", jdbcType=JdbcType.VARCHAR),
        @Result(column="cat_id", property="catId", jdbcType=JdbcType.INTEGER),
        @Result(column="book_name", property="bookName", jdbcType=JdbcType.VARCHAR),
        @Result(column="author_name", property="authorName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BookCrawlSource> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, bookCrawlSource, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, bookCrawlSource, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BookCrawlSource record) {
        return MyBatis3Utils.insert(this::insert, record, bookCrawlSource, c ->
            c.map(id).toProperty("id")
            .map(bookId).toProperty("bookId")
            .map(sourceId).toProperty("sourceId")
            .map(sourceName).toProperty("sourceName")
            .map(sourceBookId).toProperty("sourceBookId")
            .map(catId).toProperty("catId")
            .map(bookName).toProperty("bookName")
            .map(authorName).toProperty("authorName")
            .map(createTime).toProperty("createTime")
            .map(updateTime).toProperty("updateTime")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertMultiple(Collection<BookCrawlSource> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, bookCrawlSource, c ->
            c.map(id).toProperty("id")
            .map(bookId).toProperty("bookId")
            .map(sourceId).toProperty("sourceId")
            .map(sourceName).toProperty("sourceName")
            .map(sourceBookId).toProperty("sourceBookId")
            .map(catId).toProperty("catId")
            .map(bookName).toProperty("bookName")
            .map(authorName).toProperty("authorName")
            .map(createTime).toProperty("createTime")
            .map(updateTime).toProperty("updateTime")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BookCrawlSource record) {
        return MyBatis3Utils.insert(this::insert, record, bookCrawlSource, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(bookId).toPropertyWhenPresent("bookId", record::getBookId)
            .map(sourceId).toPropertyWhenPresent("sourceId", record::getSourceId)
            .map(sourceName).toPropertyWhenPresent("sourceName", record::getSourceName)
            .map(sourceBookId).toPropertyWhenPresent("sourceBookId", record::getSourceBookId)
            .map(catId).toPropertyWhenPresent("catId", record::getCatId)
            .map(bookName).toPropertyWhenPresent("bookName", record::getBookName)
            .map(authorName).toPropertyWhenPresent("authorName", record::getAuthorName)
            .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<BookCrawlSource> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, bookCrawlSource, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<BookCrawlSource> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, bookCrawlSource, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<BookCrawlSource> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, bookCrawlSource, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<BookCrawlSource> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, bookCrawlSource, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(BookCrawlSource record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(bookId).equalTo(record::getBookId)
                .set(sourceId).equalTo(record::getSourceId)
                .set(sourceName).equalTo(record::getSourceName)
                .set(sourceBookId).equalTo(record::getSourceBookId)
                .set(catId).equalTo(record::getCatId)
                .set(bookName).equalTo(record::getBookName)
                .set(authorName).equalTo(record::getAuthorName)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(BookCrawlSource record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(bookId).equalToWhenPresent(record::getBookId)
                .set(sourceId).equalToWhenPresent(record::getSourceId)
                .set(sourceName).equalToWhenPresent(record::getSourceName)
                .set(sourceBookId).equalToWhenPresent(record::getSourceBookId)
                .set(catId).equalToWhenPresent(record::getCatId)
                .set(bookName).equalToWhenPresent(record::getBookName)
                .set(authorName).equalToWhenPresent(record::getAuthorName)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BookCrawlSource record) {
        return update(c ->
            c.set(bookId).equalTo(record::getBookId)
            .set(sourceId).equalTo(record::getSourceId)
            .set(sourceName).equalTo(record::getSourceName)
            .set(sourceBookId).equalTo(record::getSourceBookId)
            .set(catId).equalTo(record::getCatId)
            .set(bookName).equalTo(record::getBookName)
            .set(authorName).equalTo(record::getAuthorName)
            .set(createTime).equalTo(record::getCreateTime)
            .set(updateTime).equalTo(record::getUpdateTime)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BookCrawlSource record) {
        return update(c ->
            c.set(bookId).equalToWhenPresent(record::getBookId)
            .set(sourceId).equalToWhenPresent(record::getSourceId)
            .set(sourceName).equalToWhenPresent(record::getSourceName)
            .set(sourceBookId).equalToWhenPresent(record::getSourceBookId)
            .set(catId).equalToWhenPresent(record::getCatId)
            .set(bookName).equalToWhenPresent(record::getBookName)
            .set(authorName).equalToWhenPresent(record::getAuthorName)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .set(updateTime).equalToWhenPresent(record::getUpdateTime)
            .where(id, isEqualTo(record::getId))
        );
    }
}