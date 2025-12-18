package com.zavga.news.service;

import com.zavga.news.model.Article;
import com.zavga.news.model.NewsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    private final String API_KEY = "3ca1392242724fcf888ce6612a501214";
    private final String BASE_URL = "https://newsapi.org/v2/everything?q=tesla&apiKey=" + API_KEY;

    public List<Article> fetchAllNews() {
        RestTemplate restTemplate = new RestTemplate();
        NewsResponse response = restTemplate.getForObject(BASE_URL, NewsResponse.class);

        if (response != null && response.getArticles() != null) {
            return response.getArticles();
        }
        return new ArrayList<>();
    }
}
