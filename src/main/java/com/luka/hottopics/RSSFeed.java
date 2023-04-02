package com.luka.hottopics;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.String;
import java.util.List;

public class RSSFeed {

    public List<List<String>> listOfTitles;
    public HashMap<String, Integer> wordCountMap;
    public String rssFeedUrl;

    RSSFeed(String source) {
        listOfTitles = new ArrayList<>();
        wordCountMap = new HashMap<>();
        rssFeedUrl = source;
    }

}
