#!/bin/sh

COMMAND="mvn -Dspring.profiles.active=test clean verify"

echo "Run command: " $COMMAND
eval $COMMAND
