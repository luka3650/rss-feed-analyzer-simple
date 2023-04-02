package com.luka.hottopics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FindHotTopics {


    public void findHotTopics(List<RSSFeed> rssFeedList) {

        // Make a union of hash maps based on the keys that appear in every map
        Map<String, Integer> combinedHashMap = new HashMap<>();

        for (RSSFeed rssFeed : rssFeedList) {
            for (Map.Entry<String, Integer> wordMap : rssFeed.wordCountMap.entrySet()) {
                String key = wordMap.getKey();
                int value = wordMap.getValue();

                // Check if word is present in all of maps
                boolean valueInAllMaps = true;
                for (RSSFeed allMaps : rssFeedList) {
                    if (!allMaps.wordCountMap.containsKey(key)) {
                        valueInAllMaps = false;
                        break;
                    }
                }
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

        System.out.println(combinedHashMap);


        // Make a hash map of keywords that are present in all RSS feeds, also get the maximum ocurrence number (hot topic occurrence)
        int maxValue = 0;
        for (Map.Entry<String, Integer> entry : combinedHashMap.entrySet()) {
            int value = entry.getValue();
            if (value >= maxValue) {
                maxValue = value;
            }
        }

        System.out.println("Max value of occurrences: " + maxValue);

        // Remove all other topics except the hot topics
        Iterator<Map.Entry<String, Integer>> iterator = combinedHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue() < maxValue)
                iterator.remove();
        }


        System.out.println("Hot topics: " + combinedHashMap);

    }


    public void countWordsInTitles(List<List<String>> titles, Map<String, Integer> wordCountMap) {

        for (List<String> title : titles) {
            for (String word : title) {
                // Check if given word is already in map, if not return default value
                int count = wordCountMap.getOrDefault(word, 0);
                wordCountMap.put(word, count + 1);
            }
        }
    }

}
