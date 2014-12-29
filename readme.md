# Simple exposition of Activiti process engine

Usable as a wisdom extension.

This code in strongly inspired from the code of [Activiti in Action](http://www.manning.com/rademakers2/).

Add configuration in src/main/configuration/system.properties

    jdbc.activiti.driver=org.postgresql.Driver
    jdbc.activiti.url=jdbc:postgresql:activitidb
    jdbc.activiti.logStatements=true
    jdbc.activiti.username=test
    jdbc.activiti.password=test

Fast deploy in felix.fileinstall.dir:

    mvn clean install -DskipTests -Dfelix.fileinstall.dir=path/to/felix_fileinstall






