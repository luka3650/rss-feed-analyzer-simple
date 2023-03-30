# Exercise

Implement a hot topic analysis for RSS feeds in Java (any version).

## Specification
Your application should be able to read array of strings (at least two RSS URLs) as input from command line.

### Workflow:
When the RSS URLs are provided through command line entry, your code should do a HTTP request to fetch the RSS feeds.
Your code should then analyse the entries in this feeds and find potential hot topics --> are there any overlaps between the news.

### Example:
RSS Feed one contains following news:
To Democrats, Donald Trump Is No Longer a Laughing Matter
Burundi military sites attacked, 12 insurgents killed
San Bernardino divers return to lake seeking electronic evidence

RSS Feed two contains following news:
Attacks on Military Camps in Burundi Kill Eight
Saudi Women to Vote for First Time
Platini Dealt Further Blow in FIFA Presidency Bid

Your analysis should return that there are news related to Burundi in both feeds.

## Additional Information
You are not limited to any additional framework.

### Commiting
You will provide your solution by forking this branch and sending us the link to you solution once you are finished with the assignment.
