# Maven archetype for Jakarta EE 9

This project provides a simple Maven archetype for generating a Jakarta EE 9 project.

## Prerequisites

* Java 8 +
* Apache Maven 3.6

## Build

Follow the steps to build this project firstly.

1. Checkout the source codes.

   ```bash
   git clone https://github.com/hantsy/maven-archetype-jakartaee9
   ```
   
2. Then build the project.

   ```bash
   mvn clean install
   ```

## Generate a Jakarta EE 9 project

Once the project is built and installed into your local Maven repository.

Run the following command to generate a new Jakarat EE 9 project using the archetype.

```bash
mvn archetype:generate
-DarchetypeGroupId=org.eclipse.ee4j 
-DarchetypeArtifactId=maven-archetype-jakartaee9 
-DarchetypeVersion=1.0.0-SNAPSHOT  
-DgroupId=com.example 
-DartifactId=myapp
-Dversion=0.0.1 
```



## Resources

* [Configuring Apache Maven for use with GitHub Packages](https://docs.github.com/en/packages/guides/configuring-apache-maven-for-use-with-github-packages#installing-a-package)
* [Publish JAR To Central Maven Repository](http://tutorials.jenkov.com/maven/publish-to-central-maven-repository.html)
* [How to Publish Your Artifacts to Maven Central](https://dzone.com/articles/publish-your-artifacts-to-maven-central)
* [Create your own Maven Archetype in 5 simple steps](https://rieckpil.de/create-your-own-maven-archetype-in-5-simple-steps/)