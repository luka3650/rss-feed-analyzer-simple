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

    public List<RSSFeed> readRss(String[] urlArray, List<String> stopWords) throws IOException, FeedException {


        // List of RSS feed objects
        List<RSSFeed> rssFeedList = new ArrayList<>();

        // Iterate given RSS URL's and save their respective news
        for (String source : urlArray) {
            URL feedURL = new URL(source);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedURL));
            RSSFeed rssFeed = new RSSFeed();

            for (Object o : feed.getEntries()) {
                SyndEntry syndEntry = (SyndEntry) o;

                // add original title in our rss feed titles list
                rssFeed.listOfNewsTitles.add(syndEntry.getTitle());
                // add parsed title inpur rss feed parsed titles list
                List<String> parsedTitle = Stream.of(syndEntry.getTitle().toLowerCase()
                                .replaceAll(REGEX_REMOVE_SIGNS, "")
                                .split(SPLIT_REGEX))
                        .collect(Collectors.toList());
                parsedTitle.removeAll(stopWords);
                rssFeed.listOfParsedTitles.add(parsedTitle);
            }

            // Fill our RSS feeds collection
            rssFeedList.add(rssFeed);
        }

        return rssFeedList;



    }


}

