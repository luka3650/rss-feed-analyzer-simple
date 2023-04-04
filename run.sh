#!/bin/bash

if [ ! -d "target" ]; then
  mvn clean
  mvn install
fi

mvn exec:java -Dexec.mainClass="com.luka.hottopics.RSSFeedAnalyzerApp"

