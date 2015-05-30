#!/bin/sh

COMMAND="mvn -Dspring.profile=test clean verify"

echo "Run command: " $COMMAND
eval $COMMAND
