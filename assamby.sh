#!/bin/bash

if [ -d "comparator" ]; then
  rm -rf "comparator"
fi

mkdir comparator

mvn clean package -DskipTests
cp launcher.sh comparator/
cp target/MuniesComparator-0.0.1-SNAPSHOT.jar comparator/
zip -r comparator.zip comparator