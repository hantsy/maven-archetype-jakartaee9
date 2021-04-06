# Building your own Maven archetype

Github is easy to share codes with others, for example I created [jakartaee9-starter-boilerplate](https://github.com/hantsy/jakartaee9-starter-boilerplate) as a project template for Jakarta EE developers. For those who are familiar with Github, it is easy to start their new projects by forking or cloning this project directly. But obviously for a general Jakarta EE application, you do not need the configuraitons of all application severs, eg. Glassfish/Payara, WildFly, OpenLiberty, Apache TomEE, etc. For most of Java developers esp. Maven users, a simple and clean Maven archetype is still the preferred option to generate the new project skeleton, and you can add the required configuration back later.

I had created some maven archetype like toys myself in the past years, but I never made them public through the Maven Central repository. In this post, I will replay the steps of creating the [Maven Archetype for Jakarta EE 9](https://github.com/hantsy/maven-archetype-jakartaee9)  and the followed steps of  publishing it to the Maven Central repository and making it public to the world.

## Generating the archetype project skeleton

Maven **archetype** is the Maven specific templates used to generate a new project in the Maven build system.  Maven itself provides an official archetype (`maven-archetype-archetype`) for you to build your own Maven  archetype.

The [official archetype introduction page](https://maven.apache.org/guides/introduction/introduction-to-archetypes.html) lists all official archetypes.

Run the following command to generate the archetype project in an interactive mode.

```bash
mvn archetype:generate
```

When you hit down the *Enter* key, it will take some seconds to list all available archetypes. Every archetype is attached to a number.

Type the number of `maven-archetype-archetype` , then it will ask you to fill `groupId`, `archetypeId`, `package` and `version` of your project. After all fields are filled as expected, it will generate the project in seconds.

Alternatively, you can generate the project in a single command aka the batch mode.

```bash
mvn archetype:generate -B \
-DarchetypeGroupId=org.apache.maven.archetypes \
-DarchetypeArtifactId=maven-archetype-archetype \
-DarchetypeVersion=1.4 \
-DgroupId=io.github.hantsy \
-DartifactId=maven-archetype-jakartaee9 \
-Dversion=1.0-SNAPSHOT 
```

Note here we  use the `-B` parameter in the command line to skip the interactive questionnaire steps and run the command in **batch** mode.

More info about Maven archetype and `maven-archetype-plugin`, please check [Maven Archetype](http://maven.apache.org/archetype/).

## Exploring the project structure

Import the project into your IDEs, such as Apache NetBeans, IntelliJ IDEA, etc.

You will see the following project file structure.

```bash
├── pom.xml
└── src
    ├── main
    │   └── resources
    │       ├── archetype-resources
    │       │   ├── pom.xml
    │       │   └── src
    │       │       ├── main
    │       │       │   └── java
    │       │       │       └── App.java
    │       │       └── test
    │       │           └── java
    │       │               └── AppTest.java
    │       └── META-INF
    │           └── maven
    │               └── archetype-metadata.xml
    └── test
        └── resources
            └── projects
                └── it-basic
                    ├── archetype.properties
                    └── goal.txt
```

The structure is similar to a general Maven project, there are some difference.

1.  The *src/main/resources/archetype-resources* folder stores all template resources used to generate projects from this archetype.
2. The *src/main/resources/META-INF/maven-archetype-metadata.xml* is the archetype description file, including the properties, file sets etc.
3. The *src/test/resources/projects* includes resources to test this archetype,  including the properties applied to generate new projects and the goal run in the host `integration-test` phase.

## Preparing the archetype resources

To simplify the work, I reuse the sample codes from  [jakartaee9-starter-boilerplate](https://github.com/hantsy/jakartaee9-starter-boilerplate).

1. Copy `GreetingMessage`, `GreetingService`, `GreetingService` and `JaxrsActivator` to the *src/main/resources/archetype-resources/src/main/java* folder. Change the package declaration  to the following.

   ```java
   package $package;
   ...
   ```

2. Similar to the last step, copy the `GreetingResourceTest` and `GreetingServiceTest` to the *src/main/resources/archetype-resources/src/test/java* folder. Remember changing the package declaration.

3. Add empty `beans.xml`file to the *src/main/resources/archetype-resources/src/main/resources/META-INF/* folder. 

   ```xml
   <beans xmlns="https://jakarta.ee/xml/ns/jakartaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/beans_3_0.xsd"
           version="3.0">
   </beans>
   ```

4. Add a simple `arquillian.xml` to  the *src/main/resources/archetype-resources/src/test/resources* folder. 
   ```xml
   <arquillian
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xmlns="http://jboss.org/schema/arquillian"
               xsi:schemaLocation="http://jboss.org/schema/arquillian
                                   http://jboss.org/schema/arquillian/arquillian_1_0.xsd">
       <defaultProtocol type="Servlet 5.0"/>
   
       <engine>
           <property name="deploymentExportPath">target/</property>
       </engine>
   
       <container qualifier="glassfish" default="true">
           <configuration>
               <property name="adminHost">localhost</property>
               <property name="adminPort">4848</property>
               <property name="adminUser">admin</property>
               <!-- if https is enabled via `asadmin enable-secure-admin` on a remote server -->
               <!-- <property name="adminHttps">true</property>-->
               <!-- if admin password is changed via `asadmin change-admin-password` -->
               <!--<property name="adminPassword">adminadmin</property>-->
               <!-- default is empty -->
               <property name="adminPassword"></property>
           </configuration>
       </container>
   </arquillian>    
   ```
   
5. Change the `pom.xml` template (*src/main/resources/archetype-resources/pom.xml*) to the following.   

   ```xml
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
     <modelVersion>4.0.0</modelVersion>
     <groupId>${groupId}</groupId>
     <artifactId>${artifactId}</artifactId>
     <version>${version}</version>
     <packaging>war</packaging>
   
     <name>${artifactId}</name>
     <description>A Jakarta EE starter boilerplate for Jakarta EE 9</description>
     <properties>
       <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
       <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
       <maven.compiler.source>1.8</maven.compiler.source>
       <maven.compiler.target>1.8</maven.compiler.target>
   
       <!-- Official Maven Plugins -->
       <maven-compiler-plugin.version>3.8.1</maven-compiler-plugin.version>
       <maven-war-plugin.version>3.3.1</maven-war-plugin.version>
       <maven-dependency-plugin.version>3.1.2</maven-dependency-plugin.version>
       <maven-surefire-plugin.version>3.0.0-M5</maven-surefire-plugin.version>
       <maven-failsafe-plugin.version>3.0.0-M5</maven-failsafe-plugin.version>
       <maven-surefire-report-plugin.version>3.0.0-M5</maven-surefire-report-plugin.version>
   
       <!-- Cargo maven plugin -->
       <!-- Since 1.9.0, cargo-maven3-plugin is the default for cargo prefix. -->
       <cargo-maven3-plugin.version>1.9.0</cargo-maven3-plugin.version>
   
       <!-- Jakarta EE API -->
       <jakartaee-api.version>9.0.0</jakartaee-api.version>
   
       <!-- Arquillian BOM -->
       <arquillian-bom.version>1.7.0.Alpha9</arquillian-bom.version>
       <junit-jupiter.version>5.7.1</junit-jupiter.version>
   
       <!-- Glassfish server -->
       <glassfish.version>6.0.0</glassfish.version>
       <arquillian-glassfish6.version>1.0.0.Alpha1</arquillian-glassfish6.version>
       <jersey.version>3.0.1</jersey.version>
   
       <!-- by default skip tests -->
       <skipTests>true</skipTests>
     </properties>
   
     <dependencyManagement>
       <dependencies>
         <dependency>
           <groupId>jakarta.platform</groupId>
           <artifactId>jakarta.jakartaee-api</artifactId>
           <version>${jakartaee-api.version}</version>
           <scope>provided</scope>
         </dependency>
         <dependency>
           <groupId>org.jboss.arquillian</groupId>
           <artifactId>arquillian-bom</artifactId>
           <version>${arquillian-bom.version}</version>
           <scope>import</scope>
           <type>pom</type>
         </dependency>
         <dependency>
           <groupId>org.junit</groupId>
           <artifactId>junit-bom</artifactId>
           <version>${junit-jupiter.version}</version>
           <type>pom</type>
           <scope>import</scope>
         </dependency>
       </dependencies>
     </dependencyManagement>
   
     <dependencies>
       <dependency>
         <groupId>jakarta.platform</groupId>
         <artifactId>jakarta.jakartaee-api</artifactId>
       </dependency>
       <dependency>
         <groupId>org.jboss.arquillian.junit5</groupId>
         <artifactId>arquillian-junit5-container</artifactId>
         <scope>test</scope>
       </dependency>
       <!-- see: https://github.com/arquillian/arquillian-core/issues/248 -->
       <!-- and https://github.com/arquillian/arquillian-core/pull/246/files -->
       <dependency>
         <groupId>org.jboss.arquillian.protocol</groupId>
         <artifactId>arquillian-protocol-servlet-jakarta</artifactId>
         <scope>test</scope>
       </dependency>
       <dependency>
         <groupId>org.junit.jupiter</groupId>
         <artifactId>junit-jupiter</artifactId>
         <scope>test</scope>
       </dependency>
     </dependencies>
   
     <build>
       <finalName>${project.artifactId}</finalName>
       <pluginManagement>
         <plugins>
           <plugin>
             <groupId>org.codehaus.cargo</groupId>
             <artifactId>cargo-maven3-plugin</artifactId>
             <version>${cargo-maven3-plugin.version}</version>
           </plugin>
         </plugins>
       </pluginManagement>
       <plugins>
         <plugin>
           <groupId>org.apache.maven.plugins</groupId>
           <artifactId>maven-compiler-plugin</artifactId>
           <version>${maven-compiler-plugin.version}</version>
         </plugin>
         <plugin>
           <groupId>org.apache.maven.plugins</groupId>
           <artifactId>maven-war-plugin</artifactId>
           <version>${maven-war-plugin.version}</version>
         </plugin>
       </plugins>
     </build>
     <profiles>
       <profile>
         <id>glassfish</id>
         <activation>
           <activeByDefault>true</activeByDefault>
         </activation>
         <build>
           <plugins>
             <plugin>
               <groupId>org.codehaus.cargo</groupId>
               <artifactId>cargo-maven3-plugin</artifactId>
               <configuration>
                 <container>
                   <containerId>glassfish6x</containerId>
                   <artifactInstaller>
                     <groupId>org.glassfish.main.distributions</groupId>
                     <artifactId>glassfish</artifactId>
                     <version>${glassfish.version}</version>
                   </artifactInstaller>
                 </container>
                 <configuration>
                   <!-- the configuration used to deploy -->
                   <home>${project.build.directory}/glassfish6x-home</home>
                   <properties>
                     <cargo.remote.password></cargo.remote.password>
                   </properties>
                 </configuration>
               </configuration>
             </plugin>
           </plugins>
         </build>
   
         <dependencies>
           <!-- Jersey -->
           <dependency>
             <groupId>org.glassfish.jersey.media</groupId>
             <artifactId>jersey-media-sse</artifactId>
             <version>${jersey.version}</version>
             <scope>test</scope>
           </dependency>
           <dependency>
             <groupId>org.glassfish.jersey.media</groupId>
             <artifactId>jersey-media-json-binding</artifactId>
             <version>${jersey.version}</version>
             <scope>test</scope>
           </dependency>
           <dependency>
             <groupId>org.glassfish.jersey.inject</groupId>
             <artifactId>jersey-hk2</artifactId>
             <version>${jersey.version}</version>
             <scope>test</scope>
           </dependency>
           <dependency>
             <groupId>org.glassfish.jersey.core</groupId>
             <artifactId>jersey-client</artifactId>
             <version>${jersey.version}</version>
             <scope>test</scope>
           </dependency>
           <dependency>
             <groupId>org.jboss.arquillian.container</groupId>
             <artifactId>arquillian-glassfish-remote-6</artifactId>
             <version>${arquillian-glassfish6.version}</version>
             <scope>test</scope>
           </dependency>
         </dependencies>
   
       </profile>
     </profiles>
   </project>
   ```

   Note, the value of `groupId`, `artifactId` and `version` elements in this xml refers to a placeholder which can be intercepted in the generating stage. 

6. Change the archetype descriptor (*src/main/resources/META-INF/maven/archetype-metadata.xml*) to the following.

   ```xml
   <archetype-descriptor
                         xmlns="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/1.0.0"
                         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/1.0.0 http://maven.apache.org/xsd/archetype-descriptor-1.0.0.xsd"
                         name="${artifactId}">
   
       <fileSets>
           <fileSet filtered="true" packaged="true">
               <directory>src/main/java</directory>
           </fileSet>
           <fileSet>
               <directory>src/main/resources</directory>
           </fileSet>
           <fileSet filtered="true" packaged="true">
               <directory>src/test/java</directory>
           </fileSet>
           <fileSet>
               <directory>src/test/resources</directory>
           </fileSet>
       </fileSets>
   </archetype-descriptor>
   ```
   In the above codes, when the `filtered` attribute is set to `true`, it  will replace the placeholders(eg. `$package`, etc.) you have set in the template files with the parameters when executing `mvn archetype:generate` to generate projects. And when the `packaged` attribute is set to `true`, it will move the generated file to corresponding packages.

To test the archetype resources, there is a *archetype.properties* and a *goal.txt* file prepared in the *src/test/resources/projects/it-basic*. In the *integration-test* phase, it will generate a test project using archetype resources and replace the placeholders using the properties defined in the *archetype.properties* and execute goals in the `goal.txt` against the generated project.

I created a [Github Actions workflow](https://github.com/hantsy/maven-archetype-jakartaee9/blob/master/.github/workflows/it-with-arq-glassfish-remote.yml) to verify the generated projects.

 ## Publishing to Maven Central Repository

> Note, the steps of publishing your packages to the Maven Central repository is a little tedious, please be patient.

Once you have built the maven archetype project successfully, the next step is publishing it to Maven Central repository and make it public, there are some useful guides in Google results. I suggest you pick one or more from the following list and read it firstly.

* [Publish JAR To Central Maven Repository](http://tutorials.jenkov.com/maven/publish-to-central-maven-repository.html)
* [How to Publish Your Artifacts to Maven Central](https://dzone.com/articles/publish-your-artifacts-to-maven-central)
* [Create your own Maven Archetype in 5 simple steps](https://rieckpil.de/create-your-own-maven-archetype-in-5-simple-steps/)

1. Register an account of the [Sonatype issue tracker](https://issues.sonatype.org).
2. File a new issue on [Community Support](https://issues.sonatype.org/projects/OSSRH) to submit your request of publishing your artifact. Check [my issue#OSSRH-66424](https://issues.sonatype.org/browse/OSSRH-66424) I created for  [hantsy/maven-archetype-jakartaee9](https://github.com/hantsy/maven-archetype-jakartaee9) as an example. 
3. Follow the response of the issue,  create a new empty repo(the name is the issue id) on Github for authentication requirement. eg. [OSSRH-66424](https://github.com/hantsy/OSSRH-66424)

4. Generate GPG keys and send to key servers.
   Under Windows 10 system, you have to install GPG tools firstly. Simply run the following command to install gpg4win if you are using [Chocolatey](https://chocolatey.org/).
   
   ```bash
   choco install gpg4win
   ```
   Refresh the environment.
   
   ```bash
   refreshenv
   ```
   Then generate GPG keys.
   
   ```bash
   # gpg --full-gen-key
   gpg (GnuPG) 2.2.27; Copyright (C) 2021 g10 Code GmbH
   This is free software: you are free to change and redistribute it.
   There is NO WARRANTY, to the extent permitted by law.
   Please select what kind of key you want:
   (1) RSA and RSA (default)
   (2) DSA and Elgamal
   (3) DSA (sign only)
   (4) RSA (sign only)
   (14) Existing key from card
   Your selection? 1
   RSA keys may be between 1024 and 4096 bits long.
   What keysize do you want? (3072)
   Requested keysize is 3072 bits
   Please specify how long the key should be valid.
   0 = key does not expire
   <n>  = key expires in n days
   <n>w = key expires in n weeks
   <n>m = key expires in n months
   <n>y = key expires in n years
   Key is valid for? (0)
   Key does not expire at all
   Is this correct? (y/N) y
   
   GnuPG needs to construct a user ID to identify your key.
   
   Real name: Hantsy Bai
   Email address: hantsy@gmail.com
   Comment:
   You selected this USER-ID:
   "Hantsy Bai <hantsy@gmail.com>"
   
   Change (N)ame, (C)omment, (E)mail or (O)kay/(Q)uit? O
   We need to generate a lot of random bytes. It is a good idea to perform
   some other action (type on the keyboard, move the mouse, utilize the
   disks) during the prime generation; this gives the random number
   generator a better chance to gain enough entropy.
   gpg: AllowSetForegroundWindow(9152) failed: Access is denied.
   We need to generate a lot of random bytes. It is a good idea to perform
   some other action (type on the keyboard, move the mouse, utilize the
   disks) during the prime generation; this gives the random number
   generator a better chance to gain enough entropy.
   gpg: C:/Users/hantsy/AppData/Roaming/gnupg/trustdb.gpg: trustdb created
   gpg: key E5D9B4272348ADAE marked as ultimately trusted
   gpg: directory 'C:/Users/hantsy/AppData/Roaming/gnupg/openpgp-revocs.d' created
   gpg: revocation certificate stored as 'C:/Users/hantsy/AppData/Roaming/gnupg/openpgp-revocs.d\03B4C55DDA964D942C756D79E5D9B4272348ADAE.rev'
   public and secret key created and signed.
   
   pub   rsa3072 2021-03-30 [SC]
   03B4C55DDA964D942C756D79E5D9B4272348ADAE
   uid                      Hantsy Bai <hantsy@gmail.com>
   sub   rsa3072 2021-03-30 [E]
   ```
   Send the keys to the public key servers.
   
   ```bash
   # gpg --send-keys 03B4C55DDA964D942C756D79E5D9B4272348ADAE
   gpg: sending key E5D9B4272348ADAE to hkps://hkps.pool.sks-keyservers.net
   ```

5.  Follow the steps of [Deploying to OSSRH with Apache Maven](https://central.sonatype.org/pages/apache-maven.html) to publish your artifact. 

   > I skip the detailed steps here, please read the Synatype's [article](https://central.sonatype.org/pages/apache-maven.html) carefully.

6. Then back to the [issue#OSSRH-66424](https://issues.sonatype.org/browse/OSSRH-66424), comment and notify the moderator to publish your artifact to the Maven Central repository. In one or two hours, your artifact will be available on [Maven Central Repository Search index page](https://search.maven.org/search?q=io.github.hantsy).

## Publishing to Github Package

The [Configuring Apache Maven for use with GitHub Packages](https://docs.github.com/en/packages/guides/configuring-apache-maven-for-use-with-github-packages)  page provides detailed steps to [publish a package](https://docs.github.com/en/packages/guides/configuring-apache-maven-for-use-with-github-packages#publishing-a-package) to Github Packages. 

1. Following the steps of [Authenticating to Github Packages](https://docs.github.com/en/packages/guides/configuring-apache-maven-for-use-with-github-packages#authenticating-to-github-packages) to set the Github authentication info in your *~/.m2/settings.xml* file.

2. Deploy to Github Packages.

   In our case, we have set the `distributionManagement` to use the Maven Central repository. Use the following command instead.

   ```bash
   mvn clean package deploy -DskipTests -DaltDeploymentRepository=github::https://maven.pkg.github.com/hantsy/maven-archetype-jakartaee9
   ```
   
3. By default, Github Packages does not allow anyone to access your repository without an authentication. After [consulting from Github community](https://github.community/t/how-to-make-github-packages-to-the-public/171321), there is a solution to resolve this issue gracefully, it is possible to make your repository accessible to the public.
   
   * Generate a Personal Access Token with `packages:read` scope.
   
   * Run the following command to generate the repository config.
   
     ```bash
     # docker run ghcr.io/jcansdale/gpr encode <your PAT>
     
     A NuGet `nuget.config` file:
     <packageSourceCredentials>
     <github>
     <add key="Username" value="PublicToken" />
     <add key="ClearTextPassword" value="&#48;28d0c8f723084e499eb87a6fe173b3af295d618" />
     </github>
     </packageSourceCredentials>
     
     A Maven `pom.xml` file:
     <repositories>
     <repository>
     <id>github-public</id>
     <url>https://public:&#48;28d0c8f723084e499eb87a6fe173b3af295d618@maven.pkg.github.com/<OWNER>/*</url>
     </repository>
     </repositories>
     
     An npm `.npmrc` file:
     @OWNER:registry=https://npm.pkg.github.com
     //npm.pkg.github.com/:_authToken="\u003028d0c8f723084e499eb87a6fe173b3af295d618"
     ```
   * As you see, now you can add the above repository to your Maven proxy server or global Maven settings(*~/.m2/settings.xml*) in your system.
   
Check the [source codes from my Github](https://github.com/hantsy/maven-archetype-jakartaee9).     
