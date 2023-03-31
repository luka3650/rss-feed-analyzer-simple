import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;


public class RSSReader {


    public static void main(String[] args) throws IOException, FeedException {

        RSSReader rss  = new RSSReader();
        rss.getRSS();

    }

    public static void getRSS() throws IOException, FeedException
    {
        URL feedSource = new URL("http://rss.cnn.com/rss/edition_americas.rss");
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedSource));
        Iterator it = feed.getEntries().iterator();

        System.out.println(feed.getTitle());
        while((it.hasNext()))
        {
            SyndEntry syndEntry = (SyndEntry) it.next();
            System.out.println(syndEntry.getTitle());
        }
    }



}
