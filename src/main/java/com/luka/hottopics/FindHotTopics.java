package com.luka.hottopics;

import com.sun.syndication.io.FeedException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FindHotTopics {


    private static final String REGEX_REMOVE = "[;,:\\.$?!%]";
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
           rssFeed.setWordCountMap(findHotTopics.countWordsInTitles(rssFeed.getListOfParsedTitles()));
        }

        // Get a map of hot topics key-words and their number of appearances in given feeds
        HashMap<String, Integer> hotTopics = findHotTopics.findHotTopics(rssFeedList);


        // Get news titles related to hot topics
        HashMap<String, List<String>> hotTopicsNews = findHotTopics.getRelatedNewsTitles(rssFeedList, hotTopics);

        // Print the results
        OutputHandler.printResults(hotTopicsNews, hotTopics);

    }


    private HashMap<String, Integer> findHotTopics(List<RSSFeed> rssFeedList) {

        // Get union of RSS feed hash maps
        HashMap<String, Integer> hotTopicsMap = hashMapUnion(rssFeedList);

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


    private HashMap<String, Integer> hashMapUnion(List<RSSFeed> rssFeedList) {

        HashMap<String, Integer> combinedHashMap = new HashMap<>();
        for (RSSFeed rssFeed : rssFeedList) {
            for (Map.Entry<String, Integer> wordMap : rssFeed.getWordCountMap().entrySet()) {
                String key = wordMap.getKey();
                int value = wordMap.getValue();

                // Check if word is present in all maps
                boolean valueInAllMaps = true;
                for (RSSFeed allMaps : rssFeedList) {
                    if (!allMaps.getWordCountMap().containsKey(key)) {
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

    private HashMap<String, Integer> countWordsInTitles(List<List<String>> titles) {

        HashMap<String, Integer> wordCountMap = new HashMap<>();
        for (List<String> title : titles) {
            for (String word : title) {
                // If given word is already in map return default value
                int count = wordCountMap.getOrDefault(word, 0);
                wordCountMap.put(word, count + 1);
            }
        }

        return wordCountMap;
    }

    private HashMap<String, List<String>> getRelatedNewsTitles(List<RSSFeed> rssFeedList, HashMap<String, Integer> hotTopics) {
        HashMap<String, List<String>> hotTopicNewsTitles = new HashMap<>();

        // Map hot topics to news titles
        for (RSSFeed rssFeed : rssFeedList) {
            rssFeed.getListOfNewsTitles().forEach(title -> {
                for (Map.Entry<String, Integer> entry : hotTopics.entrySet()) {
                    String key = entry.getKey();
                    String lowerCaseTitle = title.toLowerCase();

                    // Split title string into a list and check if the hot topic is equal to any words in the title
                    List<String> titleList = Arrays.stream(lowerCaseTitle.replaceAll(REGEX_REMOVE, "")
                                    .split(" "))
                            .collect(Collectors.toList());

                    boolean contains = titleList.stream().anyMatch(word -> word.equals(key));
                    if (contains) {
                        List<String> list;
                        list = hotTopicNewsTitles.containsKey(key) ? hotTopicNewsTitles.get(key) : new ArrayList<>();
                        list.add(title);
                        hotTopicNewsTitles.put(key, list);
                    }
                }
            });
        }
        return hotTopicNewsTitles;
    }

}
