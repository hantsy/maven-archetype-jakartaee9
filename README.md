# Maven archetype for Jakarta EE 9

This project provides a simple Maven archetype for generating a Jakarta EE 9 project.

## Prerequisites

* Java 8 +
* Apache Maven 3.6

## Build
Follow the stpes to build this project.

Checkout the source codes.

```bash
git clone https://github.com/hantsy/maven-archetype-jakartaee9
```

Then build the project.

```bash
mvn clean install
```

## Generate a Jakarta EE 9 project

Once the project is built and installed into your local Maven repository.

Run the following command to generate a new Jakarat EE 9 project.

```bash
mvn archetype:generate
-DarchetypeGroupId=org.eclipse.ee4j 
-DarchetypeArtifactId=maven-archetype-jakartaee9 
-DarchetypeVersion=1.0.0-SNAPSHOT  
-DgroupId=com.example 
-DartifactId=myapp
-Dversion=0.0.1 
```
