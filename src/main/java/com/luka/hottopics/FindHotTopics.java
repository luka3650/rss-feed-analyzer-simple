package com.luka.hottopics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FindHotTopics
{


    public void findHotTopics(List<RSSFeed> rssFeedList)
    {
        Map<String, Integer> combinedHashMap = new HashMap<>();

        // Loop over all RSS feed hash maps and combine them into one
        for(RSSFeed rssFeed : rssFeedList)
        {
            for(Map.Entry<String,Integer> wordMap : rssFeed.wordCountMap.entrySet())
            {
                String key = wordMap.getKey();
                int value = wordMap.getValue();
                // Check if the key is already in map, if it is increment the value of the key else put the key in map
                if (combinedHashMap.containsKey(key)) {
                    combinedHashMap.put(key, combinedHashMap.get(key) + value);
                } else {
                    combinedHashMap.put(key, value);
                }
            }
        }

        System.out.println(combinedHashMap);


        // Make a hash map of keywords that are present in all RSS feeds, also get the maximum ocurrence number (hot topic occurrence)
        Map<String,Integer> jointTopics = new HashMap<>();
        int maxValue = 0;
        for(Map.Entry<String,Integer> entry : combinedHashMap.entrySet())
        {
            String key = entry.getKey();
            int value = entry.getValue();
            if(value >= maxValue)
            {
                // check if current keyword is present in all maps
                boolean valueInAllMaps = true;
                for(RSSFeed rssFeed : rssFeedList) {
                    if(!rssFeed.wordCountMap.containsKey(key))
                    {
                        valueInAllMaps = false;
                        break;
                    }
                }

                if(valueInAllMaps)
                {
                    jointTopics.put(key,value);
                    maxValue = value;
                }
            }
        }

        System.out.println(jointTopics);

        // Remove all other topics except the hot topics
        Iterator<Map.Entry<String, Integer>> iterator = jointTopics.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue() < maxValue)
                iterator.remove();
        }



        System.out.println("Hot topics: " + jointTopics);

    }



    public void countWordsInTitles(List<List<String>> titles, Map<String, Integer> wordCountMap) {

        for(List<String> title : titles)
        {
            for(String word : title)
            {
                // Check if given word is already in map, if not return default value
                int count = wordCountMap.getOrDefault(word, 0);
                wordCountMap.put(word, count + 1);
            }
        }
    }

}
