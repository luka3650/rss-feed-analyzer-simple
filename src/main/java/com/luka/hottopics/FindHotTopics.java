package com.luka.hottopics;

import com.sun.syndication.io.FeedException;

import java.io.IOException;
import java.util.*;

public class FindHotTopics {

    private static final InputHandler inputHandler = new InputHandler();
    private static final RSSReader rssReader = new RSSReader();
    private static final LoadResources loadResources = new LoadResources();
    private static final FindHotTopics findHotTopics = new FindHotTopics();

    public static void run() throws FeedException, IOException {

        // Process input from console
        String[] urlArray = inputHandler.processInput();

        // Load stop words that needed for parsing the rss feed news topics
        List<String> stopWords = loadResources.loadStopWords();

        // Read RSS feeds and parse for hot topics
        List<RSSFeed> rssFeedList = rssReader.readRss(urlArray, stopWords);

        // Call method to count key-words appearances in an RSS feed
        for (RSSFeed rssFeed : rssFeedList) {
            findHotTopics.countWordsInTitles(rssFeed.listOfParsedTitles, rssFeed.wordCountMap);
        }

        // Get a map of hot topics key-words and their number of appearances in given feeds
        Map<String, Integer> hotTopics = findHotTopics.findHotTopics(rssFeedList);

        // Get news titles related to hot topics
        Map<String, List<String>> hotTopicsNews = findHotTopics.getRelatedNewsTitles(rssFeedList,hotTopics);

        // Print the results
        OutputHandler.printResults(hotTopicsNews,hotTopics);

    }


    private Map<String, Integer> findHotTopics(List<RSSFeed> rssFeedList) {

        // Get union of RSS feed hash maps
        Map<String, Integer> hotTopicsMap = hashMapUnion(rssFeedList);

        // Find the max occurrence value - hot topic occurrence value
        int maxValue = 0;
        for (Map.Entry<String, Integer> entry : hotTopicsMap.entrySet()) {
            int value = entry.getValue();
            if (value >= maxValue) {
                maxValue = value;
            }
        }

        // Remove all other topics except the hot topics (topics with most occurrences)
        Iterator<Map.Entry<String, Integer>> iterator = hotTopicsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue() < maxValue)
                iterator.remove();
        }

        return hotTopicsMap;
    }


    private Map<String, Integer> hashMapUnion(List<RSSFeed> rssFeedList) {

        Map<String, Integer> combinedHashMap = new HashMap<>();
        for (RSSFeed rssFeed : rssFeedList) {
            for (Map.Entry<String, Integer> wordMap : rssFeed.wordCountMap.entrySet()) {
                String key = wordMap.getKey();
                int value = wordMap.getValue();

                // Check if word is present in all maps
                boolean valueInAllMaps = true;
                for (RSSFeed allMaps : rssFeedList) {
                    if (!allMaps.wordCountMap.containsKey(key)) {
                        valueInAllMaps = false;
                        break;
                    }
                }
                // We are adding a new element to the combined Map only if it's present in all given feeds
                // If key already in map increment the value of the key
                // Else put the key in map
                if (combinedHashMap.containsKey(key) && valueInAllMaps) {
                    combinedHashMap.put(key, combinedHashMap.get(key) + value);
                } else {
                    if (valueInAllMaps)
                        combinedHashMap.put(key, value);
                }
            }
        }

        return combinedHashMap;
    }

    private void countWordsInTitles(List<List<String>> titles, Map<String, Integer> wordCountMap) {

        for (List<String> title : titles) {
            for (String word : title) {
                // If given word is already in map return default value
                int count = wordCountMap.getOrDefault(word, 0);
                wordCountMap.put(word, count + 1);
            }
        }
    }

    private Map<String, List<String>> getRelatedNewsTitles(List<RSSFeed> rssFeedList, Map<String, Integer> hotTopics)
    {
        Map<String, List<String>> hotTopicNewsTitles = new HashMap<>();

        // Map hot topics to news titles
        for(RSSFeed rssFeed : rssFeedList)
        {
            rssFeed.listOfNewsTitles.forEach(title -> {
                for(Map.Entry<String,Integer> entry : hotTopics.entrySet())
                {
                    String key = entry.getKey();
                    String lowerCaseTitle = title.toLowerCase();

                    // Check for hot topic in lower cased news title
                    if(lowerCaseTitle.contains(key)) {
                        List<String> list;
                        list = hotTopicNewsTitles.containsKey(key) ? hotTopicNewsTitles.get(key) : new ArrayList<>();
                        list.add(title);
                        hotTopicNewsTitles.put(key,list);
                    }
                }
            });
        }
        return hotTopicNewsTitles;
    }

}
