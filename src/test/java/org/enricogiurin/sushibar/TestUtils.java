package org.enricogiurin.sushibar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by enrico on 7/28/17.
 */
public class TestUtils {

    public static String extractLink(String html) {
        Document doc;
        doc = Jsoup.parse(html);
        Elements links = doc.select("a[href]");
        return links.get(0).attr("href");
    }


}
