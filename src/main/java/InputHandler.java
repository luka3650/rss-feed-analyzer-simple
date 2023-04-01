import com.sun.syndication.io.FeedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class InputHandler {
    public static final String SPLIT_REGEX = " ";
    public static final String AT_LEAST_TWO_RSS = "At least two RSS URL's should be given!";
    public static final Integer URL_MIN_NUM = 2;
    public static String STOPWORDS_TARGET_FILE = "stopwords.txt";


    public static void main(String[] args) throws InputException, IOException, FeedException {

        InputHandler inputHandler = new InputHandler();

        // Process input from console
        var url_array = inputHandler.processInput();

        // Load stop words that needed for parsing the rss feed news topics
        var stop_words = inputHandler.loadStopWords();

        RSSReader rssReader = new RSSReader();

        rssReader.readRss(url_array, stop_words);

    }

    private String[] processInput() throws InputException {
        System.out.print("Enter RSS URL's separated with spaces: ");
        Scanner scanner = new Scanner(System.in);

        // Read array of URL strings input
        String[] url_array = scanner.nextLine().split(SPLIT_REGEX);

        // Check the number of given URL's
        var size = url_array.length;
        if (size < URL_MIN_NUM) {
            throw new InputException(AT_LEAST_TWO_RSS);
        }

        return url_array;
    }

    private List<String> loadStopWords() {

       List<String> stopWords = new ArrayList<String>();
        InputStream inputStreamInstance = getClass().getResourceAsStream(STOPWORDS_TARGET_FILE);
        Scanner scanner = new Scanner(inputStreamInstance);

        while (scanner.hasNext())
            stopWords.add(scanner.next().toLowerCase().trim());

        return stopWords;
    }

}