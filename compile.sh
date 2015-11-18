#!/bin/sh
mvn clean
mvn package
rm ../../test-server-1.8/plugins/AchievementAwards-0.1.jar
cp ./target/AchievementAwards-0.1.jar ../../test-server-1.8/plugins/
