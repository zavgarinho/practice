package com.zavga.news.controller;

import com.zavga.news.model.Article;
import com.zavga.news.model.ConfigProperties;
import com.zavga.news.service.NewsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
//@RefreshScope
public class NewsController {

    private final NewsService newsService;
    private List<Article> cachedNews;


    private ConfigProperties pageTitle;

    public NewsController(NewsService newsService, ConfigProperties configProperties) {
        this.newsService = newsService;
        this.pageTitle = configProperties;
    }

    @GetMapping("/")
    public String showNews(@RequestParam(defaultValue = "0") int index, Model model) {
        System.out.println(this.newsService.getBASE_URL());
        if (cachedNews == null || cachedNews.isEmpty()) {
            cachedNews = newsService.fetchAllNews();
        }
        if (cachedNews.isEmpty()) {
            model.addAttribute("error", "Новини не знайдені");
            return "index";
        }
        if (index < 0) index = 0;
        if (index >= cachedNews.size()) index = cachedNews.size() - 1;
        Article currentArticle = cachedNews.get(index);
        model.addAttribute("pageTitle", this.pageTitle);
        System.out.println(this.pageTitle);
        model.addAttribute("article", currentArticle);
        model.addAttribute("currentIndex", index);
        model.addAttribute("totalCount", cachedNews.size());
        model.addAttribute("hasPrevious", index > 0);
        model.addAttribute("hasNext", index < cachedNews.size() - 1);
        return "index";
    }
}