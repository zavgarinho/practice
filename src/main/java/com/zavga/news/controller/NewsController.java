package com.zavga.news.controller;

import com.zavga.news.model.Article;
import com.zavga.news.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class NewsController {

    private final NewsService newsService;
    private List<Article> cachedNews; // Кэш новостей

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/")
    public String showNews(@RequestParam(defaultValue = "0") int index, Model model) {
        // 1. Загружаем новости, если их нет
        if (cachedNews == null || cachedNews.isEmpty()) {
            cachedNews = newsService.fetchAllNews();
        }

        // Защита: если новостей вообще нет
        if (cachedNews.isEmpty()) {
            model.addAttribute("error", "Новостей не найдено");
            return "index";
        }

        // Защита границ индекса (чтобы не уйти в минус или дальше конца)
        if (index < 0) index = 0;
        if (index >= cachedNews.size()) index = cachedNews.size() - 1;

        // 2. Берем нужную статью
        Article currentArticle = cachedNews.get(index);

        // 3. Передаем данные в шаблон
        model.addAttribute("article", currentArticle);
        model.addAttribute("currentIndex", index);
        model.addAttribute("totalCount", cachedNews.size());

        // Логика видимости кнопок
        model.addAttribute("hasPrevious", index > 0);
        model.addAttribute("hasNext", index < cachedNews.size() - 1);

        return "index"; // Имя файла шаблона (news.html)
    }
}