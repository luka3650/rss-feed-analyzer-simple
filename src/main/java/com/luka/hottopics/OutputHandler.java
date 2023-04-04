package com.luka.hottopics;

import java.util.List;
import java.util.Map;

public class OutputHandler {

    public static void printResults( Map<String, List<String>> hotTopicsNews, Map<String, Integer> hotTopics)
    {
        for(Map.Entry<String, List<String>> entry : hotTopicsNews.entrySet())
        {
            System.out.println("Hot topic: { " + entry.getKey() + " } , Count: " + hotTopics.get(entry.getKey()));
            System.out.println("---------Related titles---------");
            for(var title : entry.getValue())
            {
                System.out.println(title);
            }
            System.out.println();
        }

        if(hotTopics.isEmpty())
            System.out.println("There are no hot topics between these RSS feeds!");
        
    }
}
