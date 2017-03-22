package web_page_handler.html_handler;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by BraslavetsIryna on 02.03.2017.
 */
public class HtmlHandlerByUrl implements HtmlHandler {

    public StringBuilder handleWebPage(String url) throws IOException {
        URL targetUrl = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) targetUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Chrome/41.0.2228.0");

        return getWebPage(connection);
    }

    private StringBuilder getWebPage(HttpsURLConnection connection) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder result = new StringBuilder();

        String nextString;

        while ((nextString=reader.readLine())!=null){
            result.append(nextString.toLowerCase());
        }
        return getBody(result);
    }

    private StringBuilder getBody(StringBuilder htmlDocument) {
        int openBodyTag = htmlDocument.indexOf("<body");
        int closeBodyTag  = htmlDocument.indexOf("</body");
        return new StringBuilder(htmlDocument.substring(openBodyTag, closeBodyTag));
    }

    public static void main(String[] args) throws IOException {
        /*HtmlHandler handler = new HtmlHandler();
        System.out.println(handler.handleWebPage("https://eda.ru/recepty/osnovnye-blyuda/kurica-pikasso-25902"));*/
    }
}
