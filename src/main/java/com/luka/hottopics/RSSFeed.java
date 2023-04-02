package com.luka.hottopics;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.String;
import java.util.List;

public class RSSFeed {

    public List<List<String>> listOfParsedTitles;
    public HashMap<String, Integer> wordCountMap;
    public List<String> listOfNewsTitles;

    RSSFeed() {
        listOfParsedTitles = new ArrayList<>();
        listOfNewsTitles = new ArrayList<>();
        wordCountMap = new HashMap<>();
    }

}
