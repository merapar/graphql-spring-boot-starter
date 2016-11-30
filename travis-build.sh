#!/bin/bash
set -ev

echo "Start checking build profile for tag \"${TRAVIS_TAG}\""

if [ "x${TRAVIS_TAG}" != "x" ]; then
    echo "Release build from tag"
    mvn clean install -Dtravis=release -B -V
else
    echo "Normal build"
    mvn clean install -B -V
fi