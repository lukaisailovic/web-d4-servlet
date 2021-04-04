package com.web_d4;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlResponse {

    private String html;

    public HtmlResponse(String html){
        this.html = html;
    }

    private final HashMap<String,String> parameters = new HashMap<>();
    private static final String START_PATTERN = "{{";
    private static final String END_PATTERN = "}}";

    public void addParameter(String key, String value){
        this.parameters.put(key,value);
    }

    private void replaceParameters(){
        String regexString = Pattern.quote(START_PATTERN) + "(.*?)" + Pattern.quote(END_PATTERN);
        Pattern pattern = Pattern.compile(regexString);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String textInBetween = matcher.group(1);
            if (this.parameters.containsKey(textInBetween)){
                String regexReplaceString = Pattern.quote(START_PATTERN) + textInBetween + Pattern.quote(END_PATTERN);
                this.html = this.html.replaceAll(regexReplaceString,this.parameters.get(textInBetween));
            }
        }
    }

    public String getHtml() {
        this.replaceParameters();
        return html;
    }
}
