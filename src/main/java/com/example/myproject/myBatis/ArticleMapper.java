package com.example.myproject.myBatis;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ArticleMapper {
    String insert = "INSERT INTO ARTICLES (TITLE, AUTHOR ) VALUES (#{title}, #{author})";

    @Select("SELECT * FROM ARTICLES ")
    List<Article> getArticle();

    @Insert(insert)
    void insert(Article article);
}
