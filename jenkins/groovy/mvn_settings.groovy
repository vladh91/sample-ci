import jenkins.model.*
import org.jenkinsci.plugins.configfiles.maven.*
import org.jenkinsci.plugins.configfiles.maven.security.*

def configStore = Jenkins.instance.getExtensionList('org.jenkinsci.plugins.configfiles.GlobalConfigFiles')[0]

println("Setting maven settings xml")
def serverCreds = new ArrayList()


//server id as in your pom file
def serverId = 'artifactory'

//credentialId from credentials.groovy
def credentialId = 'artifactoryCredentials'

serverCredentialMapping = new ServerCredentialMapping(serverId, credentialId)
serverCreds.add(serverCredentialMapping)


def configId =  'maven_settings'
def configName = 'myMavenConfig for jenkins automation example'
def configComment = 'Global Maven Settings'
def configContent  = '''<?xml version="1.0" encoding="UTF-8"?>
<settings xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd" xmlns="http://maven.apache.org/SETTINGS/1.1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<servers>
   <server>
     <id>artifactory</id>
     <username>admin</username>
     <password>password</password>
   </server>
  </servers>
</settings>
'''

def globalConfig = new GlobalMavenSettingsConfig(configId, configName, configComment, configContent, true, serverCreds)
configStore.save(globalConfig)

println("maven settings complete")
