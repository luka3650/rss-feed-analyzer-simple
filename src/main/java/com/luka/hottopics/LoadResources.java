package com.luka.hottopics;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoadResources {

    public static String STOPWORDS_TARGET_FILE = "stopwords.txt";

    // Read all stop words and store them into a List
    public List<String> loadStopWords() {

        List<String> stopWords = new ArrayList<>();

        // Use class loader since target file is not in the same package as the class
        InputStream inputStreamInstance = getClass().getClassLoader().getResourceAsStream(STOPWORDS_TARGET_FILE);

        if(inputStreamInstance != null)
        {
            Scanner scanner = new Scanner(inputStreamInstance);
            while (scanner.hasNext())
                stopWords.add(scanner.next().toLowerCase().trim());
        }

        return stopWords;
    }
}
