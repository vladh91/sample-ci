#!/bin/bash
# Create Jenkins Downloads folder if doesnt exists.
mkdir -p jenkins/downloads

# Download needed files
# JDK 8
wget -c --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.tar.gz -P jenkins/downloads/
# Apache Maven
wget http://www-eu.apache.org/dist/maven/maven-3/3.5.2/binaries/apache-maven-3.5.2-bin.tar.gz -P jenkins/downloads

# clean docker-compose
docker-compose down
# build docker-compose environment
docker-compose build --no-cache
# start docker-compose
docker-compose up
