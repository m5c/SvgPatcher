# XML Parse Demo

Minimal project to demonstrate xml handling using pom files.

## About

This project parts from the code presented on [mkyong](https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/).  
Input files and handling have been adapted for the purpose of ```pom.xml```handling.

## How to run

 * ```cd``` into project.
 * Once, add this VM argument as maven default (Allows dynamic loading of xml doctype definitions. This is required to parse the provided svg file.):  
 ```export MAVEN_OPTS="-Djavax.xml.accessExternalDTD=all"```
 * Start the software (make sure ```pom.xml``` is at call location)  
```mvn clean package exec:run```

## Procedure

 * Program parses it's own maven configuration [```pom.xml```](pom.xml).
 * Program walks through loaded tree and prints dependency info:  
```
Root Element :project
------
GroupId: junit
ArtifactId: junit
Version: 3.8.1
-----
GroupId: org.apache.logging.log4j
ArtifactId: log4j-core
Version: 2.14.1
-----
```
 * Program exports in-ram tree representation back into an xml file (```pom2.xml```).


