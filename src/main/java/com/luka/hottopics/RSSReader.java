package com.luka.hottopics;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;


public class RSSReader {

    public static final String REGEX_REMOVE_SIGNS = "[,':]";
    public static final String SPLIT_REGEX = " ";

    // List of RSS feed objects
    public List<RSSFeed> rssFeedList = new ArrayList<>();

    private FindHotTopics findHotTopics = new FindHotTopics();

    

    public void readRss(String[] urlArray, List<String> stopWords) throws IOException, FeedException {


        // Iterate given RSS URL's and save their respective news
        for (var source : urlArray) {
            URL feedSource = new URL(source);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedSource));
            RSSFeed rssFeed = new RSSFeed(source);

            // Parse all news titles from a given feed and store them into their RSS feed object
            for (Object o : feed.getEntries()) {
                SyndEntry syndEntry = (SyndEntry) o;
                // Clear news titles from redundant characters and turn it into a List
                List<String> parsedTitle = Stream.of(syndEntry.getTitle().toLowerCase().replaceAll(REGEX_REMOVE_SIGNS, "")
                                .split(SPLIT_REGEX))
                        .collect(Collectors.toList());
                parsedTitle.removeAll(stopWords);
                rssFeed.listOfTitles.add(parsedTitle);
            }

            // Call method to count key-words appearances in an RSS feed
            findHotTopics.countWordsInTitles(rssFeed.listOfTitles, rssFeed.wordCountMap);

            // Fill our RSS feeds collection
            rssFeedList.add(rssFeed);
        }


        findHotTopics.findHotTopics(rssFeedList);

    }


}

