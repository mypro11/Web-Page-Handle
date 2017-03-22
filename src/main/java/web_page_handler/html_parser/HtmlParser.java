package web_page_handler.html_parser;

/**
 * Created by brina on 3/22/17.
 */
public class HtmlParser {
    enum State {START, INSIDE_TAG}

    private final char singleBracket = '\'';
    private final char doubleBracket = '\"';
    private final char leftBracket = '<';
    private final char rightBracket = '>';
    private final char ampersand = '&';
    private final String leftComment = "<!--";
    private final String rightComment = "-->";
    private final String openScript = "<script";
    private final String closeScript = "</script>";
    private final String openStyle = "<style";
    private final String closeStyle = "</style>";
    private final String semicolon = ";";

    public StringBuilder getClearText(StringBuilder string) {
        State currentState = State.START;
        StringBuilder outputString = new StringBuilder("");

        int countOpenedTags =0;
        int indexOfClosedTag;
        char currentSymbol;
        for (int i = 0; i < string.length(); i++) {
            currentSymbol=string.charAt(i);
            if(currentState==State.START){
                if(currentSymbol==leftBracket){
                    indexOfClosedTag = getEndOpenedTag(string, i);
                    if(indexOfClosedTag == i){
                        currentState = State.INSIDE_TAG;
                        countOpenedTags++;
                    } else{
                        i = indexOfClosedTag;
                    }
                } else if(currentSymbol == ampersand){
                    i = getIndexOfLine(string, i, semicolon);
                } else{
                    outputString.append(currentSymbol);
                }
            } else if(currentState == State.INSIDE_TAG) {
                if (currentSymbol == rightBracket) {
                    countOpenedTags--;
                    if (countOpenedTags == 0) {
                        currentState = State.START;
                    }
                } else if (currentSymbol == singleBracket || currentSymbol == doubleBracket) {
                    i = getIndexOfLine(string, i, String.valueOf(currentSymbol));
                } else if (currentSymbol == leftBracket) {
                    indexOfClosedTag = getEndOpenedTag(string, i);
                    if(indexOfClosedTag == i){
                        countOpenedTags++;
                    } else{
                        i = indexOfClosedTag;
                    }
                }
            }
        }
        return outputString;
    }

    private int getEndOpenedTag(StringBuilder string, int indexOfTag){
        String opendTag = "";
        if (string.substring(indexOfTag, indexOfTag + leftComment.length()).equals(leftComment)){
            opendTag = rightComment;
        } else if(string.substring(indexOfTag, indexOfTag + openScript.length()).equals(openScript)){
            opendTag = closeScript;
        } else if(string.substring(indexOfTag, indexOfTag + openStyle.length()).equals(openStyle)){
            opendTag = closeStyle;
        }
        if(opendTag==""){
            return indexOfTag;
        } else{
            return getIndexOfLine(string, indexOfTag, opendTag);
        }
    }

    private int getIndexOfLine(StringBuilder string, int indexOfTag, String stringForSearch) {
        int index = string.indexOf(stringForSearch, indexOfTag);
        if (index < 0) {
            return indexOfTag;
        }
        return stringForSearch.length() + index - 1;
    }
}
