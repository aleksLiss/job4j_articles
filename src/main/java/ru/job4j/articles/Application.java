package ru.job4j.articles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.articles.service.SimpleArticleService;
import ru.job4j.articles.service.generator.RandomArticleGenerator;
import ru.job4j.articles.store.ArticleStore;
import ru.job4j.articles.store.WordStore;

import java.io.InputStream;
import java.util.Properties;

public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class.getSimpleName());

    public static final int TARGET_COUNT = 1_000_000;

    public static void main(String[] args) throws Exception {
        Properties properties = loadProperties();
        WordStore wordStore = new WordStore(properties);
        ArticleStore articleStore = new ArticleStore(properties);
        RandomArticleGenerator articleGenerator = new RandomArticleGenerator();
        SimpleArticleService articleService = new SimpleArticleService(articleGenerator);
        articleService.generate(wordStore, TARGET_COUNT, articleStore);
    }

    private static Properties loadProperties() {
        LOGGER.info("Загрузка настроек приложения");
        Properties properties = new Properties();
        try (InputStream in =
                     Application
                             .class
                             .getClassLoader()
                             .getResourceAsStream("application.properties")) {
            properties.load(in);
        } catch (Exception e) {
            LOGGER.error("Не удалось загрузить настройки. { }", e.getCause());
            throw new IllegalStateException();
        }
        return properties;
    }

}
