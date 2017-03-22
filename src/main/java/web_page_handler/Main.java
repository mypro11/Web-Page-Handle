package web_page_handler;

import web_page_handler.html_handler.HtmlHandler;
import web_page_handler.html_handler.HtmlHandlerByUrl;
import web_page_handler.html_parser.HtmlParser;

import java.io.IOException;

/**
 * Created by brina on 3/22/17.
 */
public class Main {
    public static void main(String[] args) {
        HtmlHandler handler = new HtmlHandlerByUrl();
        try {
            StringBuilder str = handler.handleWebPage("https://eda.ru/recepty/osnovnye-blyuda/kurica-pikasso-25902");
            HtmlParser parser = new HtmlParser();
            System.out.println(parser.getClearText(str));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
