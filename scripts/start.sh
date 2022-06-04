#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app/deploy2
PROJECT_NAME=book-aws

echo "> Copy Build Files"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> Deploy New Application"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR NAME : $JAR_NAME"

echo "> Add Execution Authority to $JAR_NAME"

chmod +x $JAR_NAME

echo "> START $JAR_NAME"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME profile = $IDLE_PROFILE"
nohup java -jar \
    -Dspring.config.location=classpath:/application-$IDLE_PROFILE.yml \
    -Dspring.profiles.active=$IDLE_PROFILE
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &