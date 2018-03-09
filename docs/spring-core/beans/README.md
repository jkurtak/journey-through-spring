<table><tr><td><a href="https://github.com/JahnelGroup/journey-through-spring">Table of Contents</a></td></tr></table>

Beans
======
Objects are the key element in Java programming and the Spring Framework handles them in a very special way. Unlike a regular Java Object that is created with the **new** operator and then used, a Spring Object must be registered with the ApplicationContext first. When an Object is registered with the ApplicationContext it is referred to as a [Bean](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-definition) (or “Managed Bean” or “Component”). When Spring manages an Object as a Bean it is creating a Proxy around your object and can do very interesting things with it.

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

## Bean Scope
The concept of [Scope](https://en.wikipedia.org/wiki/Scope_(computer_science)) is fundamental to computer programming and it’s no surprise that its found in Spring as well. As you delegate the control over the creation of your Objects to Spring you have the ability to advise Spring on scope.

| Scope               | Description                                                     |
| ------------------- | --------------------------------------------------------------- |
| singleton **(default)** | A single instance is created                                    |
| prototype           | A new instance is created each time the bean is referenced      |
| request             | A new instance is created once per web request (web-aware)      |
| session             | A new instance is created once per user web session (web-aware) |
| application         | A new instance is created once per ServletContext (web-aware)   |
| websocket           | A new instance is created once per WebSocket (web-aware)        |
| *<custom>*          | It is possible to define your own scope rules                   |

* **Read:** [Bean Scopes](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-factory-scopes)

## Bean Lifecycle
As Spring Beans are created they follow a Lifecycle where you can hook-in callback listeners to do interesting things when the Beans are first created and destroyed. 

* **Read:** [Spring Bean Life Cycle](https://www.thejavaprogrammer.com/spring-bean-life-cycle/)

A common use-case is to validate that certain properties are set on your Bean after it is created.

```kotlin
@Component
class ConnectionManager(
    @Value("\${connection.url}") var url: String = "") {

    @PostConstruct
    fun init(){
        Assert.isTrue(!url.isNullOrBlank(), "url must be set")
        Assert.isTrue(url.startsWith("http://"), "http is the only support protocol")
    }
}
```

## Accessing Beans with Dependency Injection
Once your Beans are registered with the ApplicationContext you need a way to retrieve them and this is done through Dependency Injection. Spring provides several ways to accomplish this but we tend to use one of these techniques: constructor, property, or directly from the ApplicationContext.

> **Important:** Dependency Injection is completely Spring managed, that is you *get Beans from other Beans*. The whole concept is that you retrieve a Bean from the Spring ApplicationContext and it's the containers job to construct and wire the dependencies into your Bean before giving it back to you. If you're creating a component via the **new** operator then it is "unmanaged" and Spring is unaware of it so any Spring constructs defined in your class will not work at all.

### Constructor Injection
The best way to wire you dependencies together are by defining them in your constructor. The reason this is favored is because it's very obvious what your dependencies are. Additionally it makes it easy to Unit Test your code because you have the ability to inject Mocks into the constructor. 

* **Read:** [Explain why constructor inject is better than other options](https://stackoverflow.com/questions/21218868/explain-why-constructor-inject-is-better-than-other-options)

```kotlin
@Service
class UserService { /* some code */ }

/* 
 * Spring will create a UserController Bean for you and all the dependencies
 * needed as well (i.e., userService)
 */ 
@RestController
class UserController(

    /* 
     * Spring will automatically detect that a reference to another Bean 
     * called userService is required and inject it here for you. 
     */ 
    var userService: UserService
  
){
    @RequestMapping("/user/{id}")
    fun findUserById(@RequestParam id: Long) = userService.findById(id) // now you can use it in your class
}
```

Now to test the UserController you could easily Mock out the UserService.

```kotlin
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class UserControllerTest{

    lateinit var userController

    @Before fun setup() {
      // easily inject a fake mock of the UserService
      userController = UserController(Mockito.mock(UserService::class.java))
    }

    @Test
    fun `test my`(){
        Mockito.`when`(userService.findById(Mockito.any(Long::class.java)))
          .thenReturn(User(1, "Fake User"))
        
        // continue with test
        var fakeUser: User = userController.findUserById(1);
    }

}
```

### Property Injection with @Autowired
The favored alternative to constructor injection is property level injection. This approach illustrates a lot more of the Spring magic because it's using reflection to discover properties of your class that need to be provided by Spring. Surprising to many people it can even inject **private** properties into your class. 

* **Read:** [Dangers of Field Injection](http://vojtechruzicka.com/field-dependency-injection-considered-harmful/)

```kotlin
@Service
class UserService { /* some code */ }

/* 
 * Spring will create a UserController Bean for you and all the dependencies
 * needed as well (i.e., userService)
 */ 
@RestController
class UserController{

    /* 
     * Spring will use reflection to detect that this property is a reference to 
     * another Bean called userService and inject it here for you directly after 
     * the UserController is initialized by Spring. 
     */ 
    @Autowired
    lateinit var userService: UserService

    @RequestMapping("/user/{id}")
    fun findUserById(@RequestParam id: Long) = userService.findById(id) // now you can use it in your class
}
```

> [lateinit](https://kotlinlang.org/docs/reference/properties.html#late-initialized-properties-and-variables) is a Kotlin construct and is confusing for those new using the language with Spring. This is required because Kotlin is very strict with Null Safety checking. There is a short period of time where the userService property will actually be null, refer back to the Spring Bean Lifecycle section to learn more. Spring will first create your object and then use reflection immediately after to inject these properties, lateinit essentially tells Kotin to ignore the Null safety checks for this reference. 

### Retrieve from ApplicationContext

## Exercises
Try out these exercises to get a feel for the Application Context and declaring Beans.

* **Read:** [Journey Through Spring: 01-app-context](https://github.com/JahnelGroup/journey-through-spring/tree/master/01-app-context)
* **Read:** [Journey Through Spring: 02-declare-beans](https://github.com/JahnelGroup/journey-through-spring/tree/master/02-declare-beans)
