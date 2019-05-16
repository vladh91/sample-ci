# Image of Jenkins from Docker Hub
FROM jenkins/jenkins:lts

# Created by
MAINTAINER Vlad Harutyunyan (harutyunyan.vlad@gmail.com)

#get rid of admin password setup

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"


#copy plugins list to install directory in jenkins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt

#Fix issue with line ending in windows
USER root
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix /usr/share/jenkins/ref/plugins.txt && apt-get --purge remove -y dos2unix && rm -rf /var/lib/apt/lists/*

# install plugins
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

# Copy groovy scripts for jenkins configuration
COPY groovy/* /usr/share/jenkins/ref/init.groovy.d/

# Copy MQ Notifier config file
COPY mq-notifier.xml /var/jenkins_home/

# Run as root
USER root

# Install filebeat on jenkins
RUN curl -o /tmp/filebeat_6.0.0_amd64.deb https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-6.0.0-amd64.deb && \
    dpkg -i /tmp/filebeat_6.0.0_amd64.deb &&  apt-get install

COPY filebeat.yml /etc/filebeat/filebeat.yml
RUN  chmod go-w /etc/filebeat/filebeat.yml

COPY ["entrypoint.sh", "/"]

RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/bin/bash","-c","./entrypoint.sh"]
