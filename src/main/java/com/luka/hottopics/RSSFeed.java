package com.luka.hottopics;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.String;
import java.util.List;

public class RSSFeed {

    private List<List<String>> listOfParsedTitles;
    private HashMap<String, Integer> wordCountMap;
    private List<String> listOfNewsTitles;

    RSSFeed() {
        listOfParsedTitles = new ArrayList<>();
        listOfNewsTitles = new ArrayList<>();
        wordCountMap = new HashMap<>();
    }

    public List<List<String>> getListOfParsedTitles() {
        return listOfParsedTitles;
    }

    public void setListOfParsedTitles(List<List<String>> listOfParsedTitles) {
        this.listOfParsedTitles = listOfParsedTitles;
    }

    public List<String> getListOfNewsTitles() {
        return listOfNewsTitles;
    }

    public void setListOfNewsTitles(List<String> listOfNewsTitles) {
        this.listOfNewsTitles = listOfNewsTitles;
    }

    public HashMap<String, Integer> getWordCountMap() {
        return wordCountMap;
    }

    public void setWordCountMap(HashMap<String, Integer> wordCountMap) {
        this.wordCountMap = wordCountMap;
    }

}
