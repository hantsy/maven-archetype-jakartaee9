# Maven archetype for Jakarta EE 9

This project provides a simple Maven archetype for generating a Jakarta EE 9 project.

> The work of this project is being ported to Eclipse EE4J Starter, see: [eclipse-ee4j/starter#63](https://github.com/eclipse-ee4j/starter/pull/63).

## Guide 

[Generate a Jakarta EE 9 project skeleton from Maven archetype](https://itnext.io/generate-a-jakarta-ee-9-project-skeleton-from-maven-archetype-2b9fc9ce9bb8)

## Prerequisites

* Java 8 +
* Apache Maven 3.6

## Generate a Jakarta EE 9 project

This archetype is uploaded the Maven Central Repository.

Using the following command to generate a Jakarta EE 9 project skeleton.

```bash
mvn -B  archetype:generate \
-DarchetypeGroupId=io.github.hantsy \
-DarchetypeArtifactId=maven-archetype-jakartaee9  \
-DarchetypeVersion=1.0  \
-DgroupId=com.example \
-Dpackage=com.example.demo \
-DartifactId=myapp \
-Dversion=1.0-SNAPSHOT 
```
I also uploaded a snapshot version to Github Packages. If you want to use the latst snapshot version. Add the follwing repositories settings in your `~/.m2/settings.xml`. 

```xml
<repositories>
  <repository>
    <id>github-public</id>
    <url>https://public:&#48;28d0c8f723084e499eb87a6fe173b3af295d618@maven.pkg.github.com/hantsy/*</url>
  </repository>
</repositories>
```

Once the project is generated, switch to the project root.

Run the application using the following command.

```bash
mvn clean package cargo:run
```
It will download a copy of Glassfish v6.0, and create a new domain for this project and start it immediately. After the Glassfish is running, then deploy the packaged  application on the Glassfish.

Try to test the sample REST API endpoint.

```bash
curl http://localhost:8080/myapp/api/greeting/JakartaEE
```

It is a quick way to experience the Jakarta EE 9 stack.

To run the sample testing codes. 

Firstly make sure there is a running Glassfish server. Then run the following command.

```bash
mvn clean verify -DskipTests=false
```



## Build

If you want to explore the source codes or contribute this project, follow the below steps to build the project.

1. Checkout the source codes.

   ```bash
   git clone https://github.com/hantsy/maven-archetype-jakartaee9
   ```

2. Then build the project.

   ```bash
   mvn clean install
   ```

## Resources

* [Configuring Apache Maven for use with GitHub Packages](https://docs.github.com/en/packages/guides/configuring-apache-maven-for-use-with-github-packages#installing-a-package)
* [Publish JAR To Central Maven Repository](http://tutorials.jenkov.com/maven/publish-to-central-maven-repository.html)
* [How to Publish Your Artifacts to Maven Central](https://dzone.com/articles/publish-your-artifacts-to-maven-central)
* [Create your own Maven Archetype in 5 simple steps](https://rieckpil.de/create-your-own-maven-archetype-in-5-simple-steps/)
