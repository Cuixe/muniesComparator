#!/bin/bash

DIR="$(cd "$(dirname "$0")" && pwd)"

java -jar $DIR/MuniesComparator-0.0.1-SNAPSHOT.jar 2>&1 | tee "$DIR/output.log"