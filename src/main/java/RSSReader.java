import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import javax.print.DocFlavor;


public class RSSReader {

    public static final String REGEX_REMOVE_SIGNS = ",|'|:";
    public static final String SPLIT_REGEX = " ";

    // List of RSS feed objects
    public List<RSSFeed> rssFeedList = new ArrayList<>();


    public static void main(String[] args) throws IOException, FeedException {

    }


    public void readRss(String[] url_array, List<String> stop_words) throws IOException, FeedException {


        // Iterate given RSS URL's and save their respective news
        for (var source : url_array) {
            URL feedSource = new URL(source);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedSource));
            RSSFeed rssFeed = new RSSFeed(source);

            // Parse all news titles from a given feed and store them
            for (Object o : feed.getEntries()) {
                SyndEntry syndEntry = (SyndEntry) o;
                List<String> parsedTitle = Stream.of(syndEntry.getTitle().toLowerCase().replaceAll(REGEX_REMOVE_SIGNS, "")
                                .split(SPLIT_REGEX))
                        .collect(Collectors.toList());
                parsedTitle.removeAll(stop_words);
                rssFeed.listOfTitles.add(parsedTitle);
            }

            // Call method to count key-words appearances in an RSS feed
            countWordsInTitles(rssFeed.listOfTitles, rssFeed.wordCountMap);

            // Fill our RSS feeds collection
            rssFeedList.add(rssFeed);
        }


        findHotTopics(rssFeedList);

    }

    private void countWordsInTitles(List<List<String>> titles, Map<String, Integer> wordCountMap) {

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

    private void findHotTopics(List<RSSFeed> rssFeedList)
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

        // Remove all other topics except the hot topics
        Iterator<Map.Entry<String, Integer>> iterator = jointTopics.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue() < maxValue)
                iterator.remove();
        }



        System.out.println("Hot topics: " + jointTopics);

    }
}

