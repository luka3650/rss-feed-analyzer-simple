package com.luka.hottopics;

import com.sun.syndication.io.FeedException;

import java.io.IOException;

public class RSSFeedAnalyzerApp {
    public static void main(String[] args) throws FeedException, IOException {
      FindHotTopics.run();
    }
}
