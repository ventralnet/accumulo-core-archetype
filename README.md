accumulo-core-archetype
=======================

A Maven archetype with the basic settings to get up and running with Accumulo

Purpose
-------
This archetype creates a maven project structure with

1.  A *pom.xml* with the required dependencied defined to get a connection to Accumulo up and running.
2.  A sample *App.java* with simple of examples of common accumulo operations (create table, read, write ...)

Installation
------------

To install this in your maven repository, clone *accumulo-core.archetype* to your local machine and install it with maven.

*_mvn clean install_*

Use
---

After installing the archetype into your local maven repository, to create a new project using the archetype use the 
following maven command.

*mvn archetype:generate -DarchetypeGroupId=org.apache.accumulo -DarchetypeArtifactId=accumulo-core-archetype -DarchetypeVersion=1.0-SNAPSHOT*

Post Project Creation
---------------------

After you create a new project using the archetype there are some modifications you must make to reflect your 
environment.  

1. First you will need to open up pom.xml and update the zookeeper,hadoop and accumulo versions to match the versions
you are using on your system
2. Second you will need to open App.java and update the variables for, zookeepers, instanceName, userId, and password.