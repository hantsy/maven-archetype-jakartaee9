# Generate a Jakarta EE 9 project skeleton from Maven archetype

I have created a [jakartaee9-starter-boilerplate](https://github.com/hantsy/jakartaee9-starter-boilerplate) repository for developers to building a Jakarta EE 9 project simply, you can read [some articles](https://hantsy.github.io/jakartaee9-starter-boilerplate/) I have written for the codes.

I try to introduce this project in the official Jakarta EE  community mail list, and knew that [Eclipse EE4J starter](https://github.com/eclipse-ee4j/starter) project planned to restart the Jakarta EE  introduction work. I was suggested to create a Maven archetype in the starter project that it is easier to spread to the world. So I created [another Maven archetype for Jakarta EE 9 repository](https://github.com/hantsy/maven-archetype-jakartaee9) for this purpose. At the moment I wrote down this post, this maven archetype has been approved to upload to the Maven Central Repository and Github Packages registry.

> The work of this maven archetype project is being ported to Eclipse EE4J Starter, see: [eclipse-ee4j/starter#63](https://github.com/eclipse-ee4j/starter/pull/63).

## Prerequisites

Make sure you have installed  the following software.

* Java 8 
* [Apache Maven 3.6](https://maven.apache.org)
* [GlassFish v6.0](https://glassfish.org)

> GlassFish v6.0 is still stick on Java 8, Java 11 is not supported now. GlassFish v6.1 will support Java 11, I will update it when Jakarta EE 9.1 is released.

## Generate project skeleton

Open your terminal, run the following command to generate a Jakarta EE 9 project skeleton.

```bash
> mvn -B  archetype:generate \
-DarchetypeGroupId=io.github.hantsy \
-DarchetypeArtifactId=maven-archetype-jakartaee9  \
-DarchetypeVersion=1.0  \
-DgroupId=com.example \
-Dpackage=com.example.demo \
-DartifactId=myapp \
-Dversion=1.0-SNAPSHOT 
```

Currently I only published the released version to  Maven Central repository. 

To experience the Github Packages feature,  I deployed the `1.1-SNAPSHOT` version to Github Packages. If you want to use the latest version, add the following repositories config in your `~/.m2/settings.xml`. 

```xml
<repositories>
  <repository>
    <id>github-public</id>
    <url>https://public:&#48;28d0c8f723084e499eb87a6fe173b3af295d618@maven.pkg.github.com/hantsy/*</url>
  </repository>
</repositories>
```

Once the project is generated, switch to the project root.

```bash
cd mypp
```

If you import the codes into IDE, such as NetBeans, IntelliJ IDEA, etc. you will see the following project structure.

```bash
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── demo
    │   │               ├── GreetingMessage.java
    │   │               ├── GreetingResource.java
    │   │               ├── GreetingService.java
    │   │               └── JaxrsActivator.java
    │   └── resources
    │       └── META-INF
    │           └── beans.xml
    └── test
        ├── java
        │   └── com
        │       └── example
        │           └── demo
        │               ├── GreetingResourceTest.java
        │               └── GreetingServiceTest.java
        └── resources
            └── arquillian.xml
```

1. The *pom.xml* is the project Maven  model file.
2. In the *src/main/java*,  there are 4 classes, `GreetingMessage` is a simple POJO to present a message string, `GreetingResource` is a Jakarta REST  resource,  `GreetingService` is a simple CDI bean to build `GreetingMessage`. `JaxrsActivator` is used for activating the Jakarta REST support.
3. In the *src/main/resources* folder, there is an empty `beans.xml`, which is the standard configuration file of CDI beans, but became optional since Java EE 7.
4. In the *src/test/java*, there are two test classes,  `GreetingServiceTest` is to test `GreetingService`,  `GreetingResourceTest` is to test `GreetingResource`.
5. In the *src/test/resources* folder, there is an `arquillian.xml` which is the configuration file of Arquillian testing framework.

You can simply run the application using the following command.

```bash
> mvn clean package cargo:run
[INFO] Scanning for projects...
[INFO]
[INFO] -------------------------< com.example:myapp >--------------------------
[INFO] Building com.example 1.0-SNAPSHOT
[INFO] --------------------------------[ war ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ myapp ---
[INFO] Deleting D:\tmp\myapp\target
[INFO]
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ myapp ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ myapp ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 4 source files to D:\tmp\myapp\target\classes
[INFO]
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ myapp ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ myapp ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 2 source files to D:\tmp\myapp\target\test-classes
[INFO]
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ myapp ---
[INFO] Tests are skipped.
[INFO]
[INFO] --- maven-war-plugin:3.3.1:war (default-war) @ myapp ---
[INFO] Packaging webapp
[INFO] Assembling webapp [myapp] in [D:\tmp\myapp\target\myapp]
[INFO] Processing war project
[INFO] Building war: D:\tmp\myapp\target\myapp.war
[INFO]
[INFO] --- cargo-maven3-plugin:1.9.0:run (default-cli) @ myapp ---
[INFO] [en3.ContainerRunMojo] Resolved container artifact org.codehaus.cargo:cargo-core-container-glassfish:jar:1.9.0 for container glassfish6x
[INFO] [talledLocalContainer] Parsed GlassFish version = [6.0.0]
[INFO] [talledLocalContainer] GlassFish 6.0.0 starting...
[INFO] [talledLocalContainer] Using port 4848 for Admin.
[INFO] [talledLocalContainer] Using port 8080 for HTTP Instance.
[INFO] [talledLocalContainer] Using port 7676 for JMS.
[INFO] [talledLocalContainer] Using port 3700 for IIOP.
[INFO] [talledLocalContainer] Using port 8181 for HTTP_SSL.
[INFO] [talledLocalContainer] Using port 3820 for IIOP_SSL.
[INFO] [talledLocalContainer] Using port 3920 for IIOP_MUTUALAUTH.
[INFO] [talledLocalContainer] Using port 8686 for JMX_ADMIN.
[INFO] [talledLocalContainer] Using port 6666 for OSGI_SHELL.
[INFO] [talledLocalContainer] Using port 9009 for JAVA_DEBUGGER.
[INFO] [talledLocalContainer] Distinguished Name of the self-signed X.509 Server Certificate is:
[INFO] [talledLocalContainer] [CN=hantsy-t540p,OU=GlassFish,O=Eclipse.org Foundation Inc,L=Ottawa,ST=Ontario,C=CA]
[INFO] [talledLocalContainer] Distinguished Name of the self-signed X.509 Server Certificate is:
[INFO] [talledLocalContainer] [CN=hantsy-t540p-instance,OU=GlassFish,O=Eclipse.org Foundation Inc,L=Ottawa,ST=Ontario,C=CA]
[INFO] [talledLocalContainer] Domain cargo-domain created.
[INFO] [talledLocalContainer] Domain cargo-domain admin port is 4848.
[INFO] [talledLocalContainer] Domain cargo-domain allows admin login as user "admin" with no password.
[INFO] [talledLocalContainer] Command create-domain executed successfully.
[INFO] [talledLocalContainer] Attempting to start cargo-domain.... Please look at the server log for more details.....
[INFO] [talledLocalContainer] Command delete-jdbc-resource failed.
[INFO] [talledLocalContainer] remote failure: The jdbc resource [ jdbc/__default ] cannot be deleted as it is required to be configured in the system.
[INFO] [talledLocalContainer] JDBC Connection pool DerbyPool deleted successfully
[INFO] [talledLocalContainer] Command delete-jdbc-connection-pool executed successfully.
[INFO] [talledLocalContainer] Application deployed with name myapp.
[INFO] [talledLocalContainer] Command deploy executed successfully.
[INFO] [talledLocalContainer] Application deployed with name cargocpc.
[INFO] [talledLocalContainer] Command deploy executed successfully.
[INFO] [talledLocalContainer] GlassFish 6.0.0 started on port [8080]
[INFO] Press Ctrl-C to stop the container...
```

This command will perform a series of tasks for you.

1. Firstly it will clean and built the project into a war package.
2. Then use cargo maven plugin to download a copy of Glassfish v6.0, and extract the files in the project build folder.
3. Then create a new `cargo-dommain` for this project and start it immediately. 
4. After the Glassfish is running, then deploy the packaged application to the Glassfish.

> You can check the server.log file in *myqpp/target/glassfish6x-home/cargo-domain/logs* to view the details of server startup and application deployment.

Try to test the sample REST API endpoint.

```bash
> curl http://localhost:8080/myapp/api/greeting/JakartaEE
{"message":"Say Hello to JakartaEE at 2021-03-31T16:32:39.216"}
```

Compare to my [starter template project](https://github.com/hantsy/jakartaee9-starter-boilerplate), the maven archetype provides a quicker approach to experience the Jakarta EE 9 stack.

To stop the running application, send a `CTRL+C`  key shortcut in the terminal.

```bash
[INFO] Press Ctrl-C to stop the container...
[INFO] [talledLocalContainer] GlassFish 6.0.0 is stopping...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  18:46 min
[INFO] Finished at: 2021-03-31T16:43:34+08:00
[INFO] ------------------------------------------------------------------------
[INFO] [talledLocalContainer] Waiting for the domain to stop .
[INFO] [talledLocalContainer] Command stop-domain executed successfully.
[INFO] [talledLocalContainer] GlassFish 6.0.0 is stopped
Terminate batch job (Y/N)? y

```



## Run Sample Tests

The maven archetype project provides two simple JUnit/Arquilian test classes to demonstrate techniques for testing CDI bean and REST API.

To run the sample testing codes. 

1. Firstly make sure there is a running Glassfish server, check  [Testing Jakarta EE 9 with GalssFish 6](https://github.com/hantsy/jakartaee9-starter-boilerplate/blob/master/docs/arq-glassfish.md).

2. Then run the following command.

    ```bash
    mvn clean verify -DskipTests=false
    ```

Next you can begin to add your codes to enrich the application.

