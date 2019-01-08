package crawler;

import data.ArticleData;
import util.IStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class PageParser implements Runnable {

    private HashSet<String> pages;
    Analyzator pageAnalyzator;
    private IStorage storage;


    public PageParser(List<String> inPages, IStorage storage) {

        this.storage = storage;
        this.pages = new HashSet<>();
        for(String s : inPages) {
            pages.add(s);
        }

        pageAnalyzator = new Analyzator();
    }

        @Override
        public void run() {
            List<ArticleData> articles = new ArrayList<>();

            for(String s : pages) {
                ArticleData articleData = pageAnalyzator.analyze(s);
                if(articleData == null)
                    continue;
                articles.add(articleData);
            }

            for(ArticleData a : articles) {
                storage.save(a.getTitle(),a.getAuthor(),a.getText());
            }

        }
    }

