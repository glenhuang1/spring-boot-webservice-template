# Project
A generic spring boot template application to run in Springboot or Wildfly.
* Can add multiple external properties files
* Include MongoDB, MySql, Oracle, Redis Databases connection configuration at the same time
* Include RabbitMQ Sender and Receiver function
* Include a Restful API integration function
* Implement MyBatis code
* Implement OpenAPI Swagger Configuration
* Can run in either Springboot App Mode or Wildfly Mode by changing the pom.xml file easily

## Run in Springboot App Mode

1. Go to pom.xml file
2. Comment out these two dependencies
```
<!--<exclusions>-->
    <!--<exclusion>-->
        <!--<groupId>org.springframework.boot</groupId>-->
        <!--<artifactId>spring-boot-starter-tomcat</artifactId>-->
    <!--</exclusion>-->
<!--</exclusions>-->
```
```
<!--<dependency>-->
    <!--<groupId>org.springframework.boot</groupId>-->
    <!--<artifactId>spring-boot-starter-tomcat</artifactId>-->
    <!--<scope>provided</scope>-->
<!--</dependency>-->
```

## Run in Wildfly Mode

1. Go to pom.xml file
2. Enable these two dependencies
```
<exclusions>
    <exclusion>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
    </exclusion>
</exclusions>
```
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>
```

## Enable Reload Automatically in IDEA

1. Go to pom.xml file
2. Enable this dependency
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```
3. File –> Setting –> Build, Execution, Deployment –> Compiler –> check this Build project automatically
4. Press SHIFT+CTRL+A (Win/*nix) or Command+SHIFT+A (Mac) to open a pop-up windows, type registry
5. Find and enabled this option compiler.automake.allow.when.app.running