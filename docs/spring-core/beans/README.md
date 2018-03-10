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
| *\<custom>*          | It is possible to define your own scope rules                   |

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

/** 
  * Spring will create a UserController Bean for you and all the dependencies
  * needed as well (i.e., userService)
  */ 
@RestController
class UserController(

    /** 
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

@RunWith(SpringRunner::class)
@SpringBootTest
class UserControllerTest{

    lateinit var userController

    @Before fun setup() {
      // easily inject a fake mock of the UserService
      userController = UserController(Mockito.mock(UserService::class.java))
    }

    @Test
    fun `easy test because of constructor injection`(){
        Mockito.`when`(userService.findById(Mockito.any(Long::class.java)))
          .thenReturn(User(1, "Fake User"))
        
        // continue with test
        var fakeUser: User = userController.findUserById(1);
    }

}
```

### Property Injection with @Autowired
The favored alternative to constructor injection is property level injection. This approach illustrates a lot more of the Spring magic because it's using reflection to discover properties of your class that need to be provided by Spring. Surprising to many people it can even inject **private** properties into your class. 

This approach is very convenient in our class but things change dramatically when you want to test it.   

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

> [lateinit](https://kotlinlang.org/docs/reference/properties.html#late-initialized-properties-and-variables) is a Kotlin construct and is confusing for those new using the language with Spring. This is required because Kotlin is very strict with null safety checking. There is a short period of time where the userService property will actually be null, refer back to the Spring Bean Lifecycle section to learn more. Spring will first create your Object and then use reflection immediately after to inject these properties, lateinit essentially tells Kotin to ignore the null safety checks for this reference. 

Testing code written this way is much harder because you really don't have a good clean way to override the value with a Mock. There are two general approaches to this - define another bean as the @Primary and override it in the ApplicationContext, or using Mockito and the @InjectMocks annotation.

#### Mocking @Autowired Properties with @Primary
The [@Primary](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/Primary.html) annotation allows you to define multiple beans of the same and give Spring a hint at deciding which one to choice by default if no [@Qualifier](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Qualifier.html) is used. 

This allows you to shadow a Bean in your application with a Mock. In order to do this you need to define a test configuration and load up another Bean of the exact same type and name. 

```kotlin
/**
  * Since we're declaring a new Configuration file just for testing we have to make 
  * the Spring test loader aware of it by refering to it in this annotation. Notice 
  * that you have also include your main class because once your declare the classes
  * attribute it overides the location of all configuration files!
  */ 
@SpringBootTest(classes = [DemoApplication::class, MyTest.TestConfig::class])
@RunWith(SpringRunner::class)
class MyTest{

    /**
      * This is now needed to define another Bean of the same type and mark it
      * as @Primary which essentially shadows the original one. 
      */
    class TestConfig {
      @Bean
      @Primary
      fun userService() = Mockito.mock(UserService::class.java)
    }

    /**
      * Now we have to rely on Spring to provide us references to test so we
      * inject them here. This is a big step away from "unit" testing. 
      */ 
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var userController: UserController

    @Test
    fun `test with mock primary bean`(){
        Mockito.`when`(userService.findById(Mockito.any(Long::class.java)))
          .thenReturn(User(1, "Fake User"))
          
        // continue with test
        var fakeUser: User = userController.findUserById(1);
    }

}
```

#### Mocking @Autowired Properties with Mockito @InjectMocks
Mockito can eliminate that nasty bit of TestConfig code using @Mock and @InjectMocks. The concept is same as with @Primary but instead of letting Spring inject the Beans into your test class you let Mockito do it. 

* **@Mock** - Is just like Mockito.mock(..) but registers the Mock to be used with @InjectMocks
* **@InjectMocks** - Is just like @Autowired but Mockito will swap out any beans with their matching Mocks declared by @Mock

Although powerful this approach can be a little dangerous. 

* **Read:** [Getting Started with Mockito @Mock, @Spy, @Captor and @InjectMocks](http://www.baeldung.com/mockito-annotations)
* **Read:** [@Mock and @InjectMocks](https://stackoverflow.com/questions/16467685/difference-between-mock-and-injectmocks)
* **Read:** [Why You Should Not Use InjectMocks Annotation to Autowire Fields](https://tedvinke.wordpress.com/2014/02/13/mockito-why-you-should-not-use-injectmocks-annotation-to-autowire-fields/)

```kotlin
@RunWith(SpringRunner::class)
@SpringBootTest
class MyTest{

    @Mock
    lateinit var userService: UserService

    @InjectMocks
    lateinit var userController: UserController

    @Test
    fun `test`(){
        Mockito.`when`(userService.findById(Mockito.any(Long::class.java)))
          .thenReturn(User(1, "Fake User"))
          
        // continue with test
        var fakeUser: User = userController.findUserById(1);
    }

}
```

### Retrieve from ApplicationContext
This approach is typically used when you are dynamically interacting with Beans during runtime, in other words you're unsure of which Bean you need so you need to look it up. For example if you're processing requests and need a corresponding transformer for different types of requests you wouldn't want to inject a reference to every type of transformer.

Let's define a few types of tranformers:

```kotlin
interface BrowserTransformer{
    fun transform(inbound: String): String
}

@Component class ChromeTransformer : BrowserTransformer {
    override fun transform(inbound: String): String = "Chrome:".plus(inbound)
}

@Component class FirefoxTransformer : BrowserTransformer{
    override fun transform(inbound: String): String = "Firefox:".plus(inbound)
}

@Component class IETransformer : BrowserTransformer{
    override fun transform(inbound: String): String = "IE:".plus(inbound)
}
```

Now you can retrieve them directly through the ApplicationContext:

```kotlin
@SpringBootApplication
class DemoApplication(var appContext: AbstractApplicationContext) : ApplicationRunner{
    override fun run(args: ApplicationArguments?) {

        var xform = appContext.getBean("firefoxTransformer", BrowserTransformer::class.java)
        println(xform.transform("Hello"))
    }

}

@RestController
class EchoController(var appContext: AbstractApplicationContext) {
  
  @RequestMapping("/echo")
  fun echo(str: String){
    var userAgent = getUserAgent()
    
    // this will get the corresponding bean based on its name
    var xform = appContext.getBean("${userAgent}Transformer", BrowserTransformer::class.java)
    
    return xform.transform(str)
  }
  
  fun getUserAgent(): String {
    // assume this code will return the String: "IE", "firefox", or "chrome"
  }
  
}

```

Testing code written this way can only be done by shadowing the original bean with @Primary in a TestConfig. 

## Exercises
Try out these exercises to get a feel for the Application Context and declaring Beans.

* **Read:** [Journey Through Spring: 01-app-context](https://github.com/JahnelGroup/journey-through-spring/tree/master/01-app-context)
* **Read:** [Journey Through Spring: 02-declare-beans](https://github.com/JahnelGroup/journey-through-spring/tree/master/02-declare-beans)
