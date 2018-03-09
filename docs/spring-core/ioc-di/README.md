<table><tr><td><a href="https://github.com/JahnelGroup/journey-through-spring">Table of Contents</a></td></tr></table>

Spring Core
======
There are two main concepts fundamental to Spring - [Inversion of Control](https://en.wikipedia.org/wiki/Inversion_of_control) and [Dependency Injection](https://en.wikipedia.org/wiki/Dependency_injection). Understanding the value of these concepts is critical to learning Spring. 

> *IoC is also known as dependency injection (DI). It is a process whereby objects define their dependencies, that is, the other objects they work with, only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. The container then injects those dependencies when it creates the bean. This process is fundamentally the inverse, hence the name Inversion of Control (IoC), of the bean itself controlling the instantiation or location of its dependencies by using direct construction of classes, or a mechanism such as the Service Locator pattern. - [Spring Docs](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-introduction)*

* **Read:** [Intro to Inversion of Control and Dependency Injection with Spring](http://www.baeldung.com/inversion-control-and-dependency-injection-in-spring)

## Inversion of Control (IoC) Container / Application Context
At the center of a Spring application is the [IoC Container](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans) which is represented by the [ApplicationContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContext.html) Class. It is the orchestrator of your code and manager of your Objects. It is essentially the glue to your entire application and knows everything. You start the Application Context in your main function.

* **Read:** [The “main” Method](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-first-application-main-method)

```kotlin
package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args) // starts the IoC Container (i.e., ApplicationContext)
}
```

When you start the IoC Container it will scan your application from the current package down (i.e, com.example.demo\*.\*) to create beans, provide advice, establish database connection pools, any many more things. Your main function should do nothing other than start the Spring ApplicationContext - everything from this point is now driven by Spring constructs. 

* **Read:** [Additional Capabilities of the ApplicationContext](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#context-introduction)

## Beans
Objects are the key element in Java programming and the Spring Framework handles them in a very special way. Unlike a regular Java Object that is created with the new operator and then used, a Spring Object must be registered with the ApplicationContext first. When an Object is registered with the ApplicationContext it is referred to as a [Bean](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-definition) (or “Managed Bean” or “Component”). When Spring manages an Object as a Bean it is creating a Proxy around your object and can do very interesting things with it.

### Bean Configuration
Beans can be configured in three primary ways: XML, Java, or Annotations. We focus primarily on Annotation based configuration.

* **Read:** [Annotation-based container configuration](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-annotation-config)

Spring provides a few different annotations to drive the creation of beans.

* Component
* Service
* Repository

* **Read:** [Difference...]()

```kotlin
// Creates a Singleton Bean out of this class
@Component
class UserTransformer { }

@Component
class UserContext { }

@Configuration
class MyConfig {

  @Bean fun userService() = UserService()

}

// Declares a Bean with the Service stereotype
@Service
class UserService { }

// Declares a Bean with the Repository stereotype
@Repository
class UserRepository { }


```

* **Read:** []()
* **Read:** []()
