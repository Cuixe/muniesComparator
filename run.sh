#! /bin/bash

mvn spring-boot:run -Dspring-boot.run.profiles=${MUNIES_PROFILE} > munes.log &