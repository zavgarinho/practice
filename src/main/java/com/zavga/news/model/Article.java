package com.zavga.news.model;

import lombok.Data;

@Data
public class Article {
    private String author;
    private String title;
    private String description;
    private String publishedAt;
    private String url;
}
