<table><tr><td><a href="https://github.com/JahnelGroup/journey-through-spring">Table of Contents</a></td></tr></table>

Beans
======
Objects are the key element in Java programming and the Spring Framework handles them in a very special way. Unlike a regular Java Object that is created with the new operator and then used, a Spring Object must be registered with the ApplicationContext first. When an Object is registered with the ApplicationContext it is referred to as a [Bean](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-definition) (or “Managed Bean” or “Component”). When Spring manages an Object as a Bean it is creating a Proxy around your object and can do very interesting things with it.

## Bean Configuration
Beans can be configured in three primary ways: XML, Java, or Annotations. We focus primarily on Annotation based configuration.

* **Read:** [Annotation-based container configuration](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-annotation-config)

Spring provides a few different annotations to drive the creation of beans.

| Annotation      | Location | Description                                            |
| --------------- | -------- | ------------------------------------------------------ |
| @Configuration  | class    | Indicates that this class defines @Bean's to be        |
| @Bean           | method   | Creates a Bean from the return value of the method     |
| @Component      | class    | Instruct Spring to create a Bean out of the class      |
| @Service        | class    | Same as @Component but Stereotyped as a Service        |
| @Repository     | class    | Same as @Component but Stereotyped as a Repository     |
| @Controller     | class    | Controller Bean which defines @RequestMappings for MVC |
| @RestController | class    | Same as @Controller but meant REST end-points          |

* **Read:** [Advantages of using spring stereotypes?](https://stackoverflow.com/questions/16051656/advantages-of-using-spring-stereotypes)
* **Read:** [What's the difference between @Component, @Repository & @Service annotations in Spring?](https://stackoverflow.com/questions/6827752/whats-the-difference-between-component-repository-service-annotations-in)
* **Read:** [Difference between spring @Controller and @RestController annotation
](https://stackoverflow.com/questions/25242321/difference-between-spring-controller-and-restcontroller-annotation)
* **Read:** [@RestController vs @Controller](https://www.genuitec.com/spring-frameworkrestcontroller-vs-controller/)

```kotlin
// Declare a Singleton Bean - one instance for the entire application
@Component
class UserTransformer { }

// Declare a Request Scope Bean - one instance per Web Request
@Component
@Scope("request")
class UserRequestContext { }

// Declare a class that defines definition of Beans
@Configuration
class MyConfig {

  // Declare a Session Scope Bean - one instance per Web Session
  @Bean 
  @Scope("session")
  fun userSessionContext() = UserSessionContext()

}

// Declares a Bean with the Service stereotype
@Service
class UserService { }

// Declares a Bean with the Repository stereotype
@Repository
class UserRepository { }

@RestController
class HelloController {
  @RequestMapping("/hello")
  fun hello() = "Hello, World!"
}

```

## Bean Scope and Lifecycle

* **Read:** []()
* **Read:** []()

## Exercises
Try out these exercises to get a feel for the Application Context and declaring Beans.

* **Read:** [Journey Through Spring: 01-app-context](https://github.com/JahnelGroup/journey-through-spring/tree/master/01-app-context)
* **Read:** [Journey Through Spring: 02-declare-beans](https://github.com/JahnelGroup/journey-through-spring/tree/master/02-declare-beans)
