package com.zavga.news.service;

import com.zavga.news.model.Article;
import com.zavga.news.model.NewsResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class NewsService {

    private final String API_KEY;
    private final String BASE_URL;


    public NewsService(@Value("${api-key}") String apiKey, @Value("${api.api-url}") String apiUrl) {
        this.API_KEY = apiKey;
        this.BASE_URL = apiUrl + apiKey;

    }
    public List<Article> fetchAllNews() {
        RestTemplate restTemplate = new RestTemplate();
        NewsResponse response = restTemplate.getForObject(BASE_URL, NewsResponse.class);

        if (response != null && response.getArticles() != null) {
            return response.getArticles();
        }
        return new ArrayList<>();
    }
}
