[![Join the chat at https://gitter.im/merapar/graphql-spring-boot-starter](https://badges.gitter.im/merapar/graphql-spring-boot-starter.svg)](https://gitter.im/merapar/graphql-spring-boot-starter?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://api.travis-ci.org/merapar/graphql-spring-boot-starter.svg?branch=master)](https://travis-ci.org/merapar/graphql-spring-boot-starter)
[![Code coverage](https://codecov.io/gh/merapar/graphql-spring-boot-starter/branch/master/graph/badge.svg)](https://codecov.io/gh/merapar/graphql-spring-boot-starter)
[![Latest Release](https://maven-badges.herokuapp.com/maven-central/com.merapar/graphql-spring-boot-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.merapar/graphql-spring-boot-starter/)
[![Dev version](https://api.bintray.com/packages/merapar/maven/graphql-spring-boot-starter/images/download.svg)](https://bintray.com/merapar/maven/graphql-spring-boot-starter/_latestVersion)

# GraphQL Spring boot starter

This is a Spring boot starter project for the [GraphQL Java](https://github.com/graphql-java/graphql-java) project.


# Table of Contents
 
- [Overview](#overview)
- [Getting started](#getting-started)
- [Versioning](#versioning)
- [Code of Conduct](#code-of-conduct)
- [Contributions](#contributions)
- [Acknowledgment](#acknowledgment)
- [License](#license)


### Overview

The implementation is based on Spring boot starter web project that will expose the GraphQL endpoint as rest controller.

It takes care of exposing a rest endpoint with all configured graphQL fields automatically.

The library aims for real-life usage in production with the ease of Spring Boot.


### Getting started

Check out the following documentation on [using spring boot starter](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-starter) project.

By adding GraphQL Spring boot starter as maven dependency on the application a @Controller will be created pointing to the configured request mapping with default "/v1/graphql".
During startup all components that implement "GraphQlFields" interface will be applied on the GraphQL schema exposed by the controller.

An example from the sample project:
```java
package com.merapar.graphql.sample.fields;

import com.merapar.graphql.GraphQlFields;
import graphql.schema.GraphQLFieldDefinition;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

@Component
public class HelloWorldFields implements GraphQlFields {

    @Override
    public List<GraphQLFieldDefinition> getQueryFields() {
        return Collections.singletonList(
                newFieldDefinition()
                        .type(GraphQLString)
                        .name("hello")
                        .staticValue("world")
                        .build()
        );
    }

    @Override
    public List<GraphQLFieldDefinition> getMutationFields() {
        return Collections.emptyList();
    }
}
```


#### Configuration

The following default properties can be configured via properties file:

```yaml
com.merapar.graphql:
  rootQueryName: "queries"
  rootQueryDescription: ""
  rootMutationName: "mutations"
  rootMutationDescription: ""
  requestMapping:
    path: "/v1/graphql"
  executor:
    minimumThreadPoolSizeQuery: 10
    maximumThreadPoolSizeQuery: 20
    keepAliveTimeInSecondsQuery: 30
    minimumThreadPoolSizeMutation: 10
    maximumThreadPoolSizeMutation: 20
    keepAliveTimeInSecondsMutation: 30
    minimumThreadPoolSizeSubscription: 10
    maximumThreadPoolSizeSubscription: 20
    keepAliveTimeInSecondsSubscription: 30
    
```


#### How to use the latest release with Maven

Dependency:

```xml
<dependency>
    <groupId>com.merapar</groupId>
    <artifactId>graphql-spring-boot-starter</artifactId>
    <version>1.0.2</version>
</dependency>

```


#### How to use the latest build with Maven

Add the repository:

```xml
<repository>
    <id>bintray-merapar-maven</id>
    <name>bintray</name>
    <url>http://dl.bintray.com/merapar/maven</url>
</repository>

```

Dependency:

```xml
<dependency>
    <groupId>com.merapar</groupId>
    <artifactId>graphql-spring-boot-starter</artifactId>
    <version>1.0.3-alpha</version>
</dependency>

```


### Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/merapar/graphql-spring-boot-starter/tags). 


### Code of Conduct

Please note that this project is released with a [Contributor Code of Conduct](CODE_OF_CONDUCT.md).
By contributing to this project (commenting or opening PR/Issues etc) you are agreeing to follow this conduct, so please
take the time to read it. 


### Contributions

Every contribution to make this project better is welcome: Thank you! 

In order to make this a pleasant as possible for everybody involved, here are some tips:

- Respect the [Code of Conduct](#code-of-conduct)
- Before opening an Issue to report a bug, please try the latest development version. It can happen that the problem is already solved.
- Please use Markdown to format your comments properly. If you are not familiar with that: [Getting started with writing and formatting on GitHub](https://help.github.com/articles/getting-started-with-writing-and-formatting-on-github/)
- For Pull Requests:
  - Here are some [general tips](https://github.com/blog/1943-how-to-write-the-perfect-pull-request)
  - Please be a as focused and clear as possible and don't mix concerns. This includes refactorings mixed with bug-fixes/features, see [Open Source Contribution Etiquette](http://tirania.org/blog/archive/2010/Dec-31.html) 
  - It would be good to add a automatic test. All tests are written in JUnit and optional with [Spring Boot Test](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html).


### Acknowledgment

This implementation is based on the [graphql-java](https://github.com/graphql-java/graphql-java) project.


### License

GraphQL Spring boot starter is licensed under the MIT License. See [LICENSE](LICENSE.md) for details.

Copyright (c) 2016 Merapar Technologies

[graphql-java License](https://github.com/graphql-java/graphql-java/blob/master/LICENSE.md)
