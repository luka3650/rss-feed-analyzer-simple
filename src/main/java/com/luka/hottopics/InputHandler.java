package com.luka.hottopics;

import com.sun.syndication.io.FeedException;

import java.io.IOException;
import java.util.Scanner;


public class InputHandler {
    public static final String SPLIT_REGEX = " ";
    public static final String AT_LEAST_TWO_RSS = "At least two RSS URL's should be given!";
    public static final Integer URL_MIN_NUM = 2;


    public static void main(String[] args) throws InputException, IOException, FeedException {

        InputHandler inputHandler = new InputHandler();
        RSSReader rssReader = new RSSReader();
        LoadResources loadResources = new LoadResources();
        FindHotTopics findHotTopics = new FindHotTopics();


        // Process input from console
        var urlArray = inputHandler.processInput();

        // Load stop words that needed for parsing the rss feed news topics
        var stopWords = loadResources.loadStopWords();

        // Read RSS feeds and parse for hot topics
        var rssFeedList = rssReader.readRss(urlArray, stopWords);

        // Call method to count key-words appearances in an RSS feed
        for (var rssFeed : rssFeedList) {
            findHotTopics.countWordsInTitles(rssFeed.listOfParsedTitles, rssFeed.wordCountMap);
        }
        findHotTopics.findHotTopics(rssFeedList);


    }

    // Get input from terminal and store it
    private String[] processInput() throws InputException {
        System.out.print("Enter RSS URL's separated with spaces: ");
        Scanner scanner = new Scanner(System.in);

        // Read array of URL strings input
        String[] urlArray = scanner.nextLine().split(SPLIT_REGEX);

        // Check the number of inputs
        int size = urlArray.length;
        if (size < URL_MIN_NUM) {
            throw new InputException(AT_LEAST_TWO_RSS);
        }

        return urlArray;
    }


}