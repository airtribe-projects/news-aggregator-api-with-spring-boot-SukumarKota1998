package com.airtribe.news_aggregator.repository;

import com.airtribe.news_aggregator.entity.Article;
import com.airtribe.news_aggregator.entity.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByCategoryIn(List<NewsCategory> category);
}
