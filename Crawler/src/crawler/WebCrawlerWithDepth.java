package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.FakeStorage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WebCrawlerWithDepth {
    private static final int MAX_DEPTH = 30;
    private HashSet<String> links;
    private ArrayList<String> linksForParsing;
    private int newsNum = 0;
    private Thread t;

    private List<String> regexExp;

    PrintWriter writer;


    public WebCrawlerWithDepth() {
        links = new HashSet<>();
        linksForParsing = new ArrayList<>();
        regexExp = new ArrayList<>();
        initRegex(regexExp);
    }

    private void initRegex(List<String> regexExp) {
        regexExp.add(".*jpg$");
        regexExp.add(".*png$");
        regexExp.add(".*jpeg");
        regexExp.add(".*oglasi.*");
        regexExp.add(".*indexforum.*");
        regexExp.add(".*pdf$");
        regexExp.add(".*@.*");
        regexExp.add(".*page.*");
        regexExp.add(".*Email.*Protection.*");
        regexExp.add(".*Impressum.*");
        regexExp.add(".*Uvjeti.*Korištenja.*");

    }

    public void getPageLinks(String URL, int depth, String regexFilter) {

        for(String r : regexExp) {
            if(URL.matches(r))
                return;
        }


        if ((!links.contains(URL) && (depth < MAX_DEPTH && URL.matches(regexFilter)))) {
            if (URL.matches(".*-.*")) {
                newsNum++;
            }

            if (newsNum % 100 == 0) {
                System.out.println(newsNum);
                t = new Thread(new PageParser(linksForParsing, new FakeStorage()));
                linksForParsing = new ArrayList<>();
                t.start();
            }

            try {
                if(!links.add(URL))
                    return;
                linksForParsing.add(URL);
                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");

                depth++;
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth, regexFilter);
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new WebCrawlerWithDepth().getPageLinks("http://www.dnevno.hr/", 0,".*www\\.dnevno.*");
    }
}
