# Application details

A hot topic analyzer was implemented that takes in at least two RSS URL's as an input. The app fetches given RSS feeds via URL's using external **rome** library. Program parses the titles from the fetched feeds and clears them of [stopwords](https://www.ranks.nl/stopwords) to eliminate words that give little to no information. Strings were also cleaned from digits and various interpunction signs. As a result the app writes out hot topics - key words that represent a topic, have most appearances and are present in all given feeds. Number of occurrences and associated title news with that topic are also displayed.

# Running the app

1. Clone the the repository and position yourself in the root directory.
2. Run the bash script for running the app:

```
./run.sh
```

3. After running the script URL's should be inputed separated by spaces.

# Results

An example of running the program with two RSS feeds from [cnn](https://edition.cnn.com/services/rss/) site with [link](http://rss.cnn.com/rss/edition_us.rss) and [link](http://rss.cnn.com/rss/edition.rss):
```
Hot topic: { fire } , Count: 4
---------Related titles---------
French minister under fire for Playboy magazine cover
Fire crews respond to fire at boarded up building
UPS Semi-truck goes off bridge, catches fire

Hot topic: { court } , Count: 4
---------Related titles---------
What to know about the Trump indictment on the eve of his court appearance
The former president is expected to turn himself in to law enforcement Tuesday and face more than 30 criminal charges in a Manhattan court 
Trump to appear in court and be charged in historic moment
Supreme Court won't review conviction of man sentenced to die for role in escape

```


