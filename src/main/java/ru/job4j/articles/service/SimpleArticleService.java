package ru.job4j.articles.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.articles.model.Article;
import ru.job4j.articles.model.Word;
import ru.job4j.articles.service.generator.ArticleGenerator;
import ru.job4j.articles.store.Store;

import java.util.List;
import java.util.stream.IntStream;

public class SimpleArticleService implements ArticleService {

    private final Logger LOGGER = LoggerFactory.getLogger(SimpleArticleService.class.getSimpleName());

    private final ArticleGenerator articleGenerator;

    public SimpleArticleService(ArticleGenerator articleGenerator) {
        this.articleGenerator = articleGenerator;
    }

    @Override
    public void generate(Store<Word> wordStore, int count, Store<Article> articleStore) {
        LOGGER.info("Гeнeрация статей в количестве {}", count);
        List<Word> words = wordStore.findAll();
        IntStream.range(0, count)
                .peek(i -> LOGGER.info("Сгенерирована статья № {}", i))
                .forEach(i -> articleStore.save(articleGenerator.generate(words)));
    }
}
