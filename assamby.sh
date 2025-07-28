#!/bin/bash

mvn clean package -DskipTests
cd target
mkdir comparator

cp ../launcher.sh comparator
cp MuniesComparator-0.0.1-SNAPSHOT.jar comparator

zip -r comparator.zip comparator