package crawler;

import data.ArticleData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Analyzator {
    String title;
    String author;
    String tekst;
    String imgUrl;
    private List<String> regExp;

    public Analyzator () {
        regExp = new ArrayList<>();
        init(regExp);
    }

    private void init(List<String> regExp) {
        regExp.add(".*Kategorija.*");
        regExp.add(".*Dnevno.*");
        regExp.add(".*marketing.*");
        regExp.add(".*Email.*Protection.*");
        regExp.add(".*Impressum.*");
        regExp.add(".*Uvjeti.*Korištenja.*");

    }

    public ArticleData analyze (String URL) {

        try {
            Document document = Jsoup.connect(URL).get();
            title = document.title();
            tekst = document.body().text();


            String[] tmp = title.split("\\|");


            for(String s : regExp)
                if(tmp[0].matches(s))
                    return null;

            title = title.split("\\|")[0];

            Elements elements = document.select("p");
            Elements tekstElements = elements.attr("style","text-align: justify;");
            StringBuilder str = new StringBuilder();

            for(Element i : tekstElements) {
                str.append(i.text());
                str.append("\n");
            }

            Elements authorSearch = document.select("span").attr("class","author");
            for (Element i : authorSearch) {
                String tmps = i.text();
                if(tmps.matches(".*Autor:.*")){
                    author = tmps;
                    break;
                }
            }
            tekst =str.toString();

            Elements images =
                    document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");

            ArrayList<String> choosen = new ArrayList<>();

            //Iterate images and print image attributes.
            for (Element image : images) {
                String height = image.attr("height");
                String width = image.attr("width");
                int heig;
                int wid;
                if(!height.isEmpty() && !width.isEmpty()) {
                    heig = Integer.valueOf(height);
                    wid = Integer.valueOf(width);

                    if(wid >= 600 && heig >= 400) {
                        imgUrl = image.attr("src");
                        break;
                    }
                }
            }
            ArticleData articleData = new ArticleData();

            if(author == null || imgUrl == null)
                return null;
            articleData.setAuthor(author);
            articleData.setImage(imgUrl);
            articleData.setText(tekst);
            articleData.setTitle(title);

            return articleData;

        } catch (IOException | NullPointerException e) {
        }

        return null;
    }

    public static void main(String[] args) {
        Analyzator a = new Analyzator();
        a.analyze("https://www.dnevno.hr/planet-x/misteriji/je-li-ovo-istina-koja-nam-se-skriva-franjo-tudman-je-ubijen-evo-kako-1258050/");
    }
}
