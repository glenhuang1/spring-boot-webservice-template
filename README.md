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

## Run in Google App Engine Flexible Mode

1. Create a folder 'appengine' under main folder
2. Create a file 'app.yaml'
3. Input the following config under 'app.yaml' file
```
runtime: java
api_version: '1.0'
env: flex
runtime_config:
  jdk: openjdk8
service: springboot-test-ws
threadsafe: true
network:
  instance_tag: {{tag_name}}
  name: {{project_id}}
  subnetwork_name: {{subnetwork_name}}
automatic_scaling:
  min_num_instances: 1
  max_num_instances: 2
  cpu_utilization:
    target_utilization: 0.6
resources:
  cpu: 2
  memory_gb: 8
  disk_size_gb: 10
env_variables:
  BUCKET_NAME: {{bucket_name}}
  FILE_NAME: {{file_name}}

```
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
3. Comment out this dependency
```
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>webjars-locator-jboss-vfs</artifactId>
    <version>${webjars-locator-jboss-vfs.version}</version>
</dependency>
```
4. Go to logback-spring.xml, and only open these lines. 
```
<appender name="CLOUD" class="com.google.cloud.logging.logback.LoggingAppender">
    <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
        <level>INFO</level>
    </filter>
    <log>spring-boot-webservice-template.log</log>
    <flushLevel>WARN</flushLevel>
</appender>
<root level="info">
    <appender-ref ref="CLOUD" />
</root>
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
