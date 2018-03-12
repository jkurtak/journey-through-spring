<table><tr><td><a href="https://github.com/JahnelGroup/journey-through-spring">Table of Contents</a></td></tr></table>

Events
======
The concept of [Events](https://en.wikipedia.org/wiki/Event_(computing)) is fundamental to computer programming and allows us to design components with loose coupling and high cohesion. Spring’s context is highly event driven and exposes this feature for you to listen and react to events as well as fire your own. 

* **Read:** [Event-driven programming](https://en.wikipedia.org/wiki/Event-driven_programming)
* **Read:** [What does 'low in coupling and high in cohesion' mean](https://stackoverflow.com/questions/14000762/what-does-low-in-coupling-and-high-in-cohesion-mean)
* **Read:** [Application Events and Listeners](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-application-events-and-listeners)
* **Read:** [Spring Events](http://www.baeldung.com/spring-events)
* **Read:** [Standard and Custom Events](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#context-functionality-events)

## ApplicationEvent
Spring provides a class called [ApplicationEvent](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationEvent.html) to faciliate the processing of events. Your events do not need to extend this class but it is good practice. Its constructor takes a single argument of type Object which is intended to be the *source* of the event. 

### What are good Events?
An event *source* is the Object for which the event is trigger against. For example, say you want to send an E-Mail to every user that signs up for your system. The event would be something like *UserSignedUpEvent* and the source would be the *User* object.

Events are things that *have happened* and they are past tense. UserLoggedInEvent states that a user has already logged in and you're being notified of the fact. Do not use events in place of direct method invocation, that's not the idea. They are more about a change to the conceptual design of your application and building a set of components that react to each other in a choreographed way.

**Read: * [Orchestration vs. Choreography
](https://stackoverflow.com/questions/4127241/orchestration-vs-choreography)
**Read: * [Scaling Microservices with an Event Stream](https://www.thoughtworks.com/insights/blog/scaling-microservices-event-stream)

### Events are POJO's
With the except of extending ApplicationEvent for best practice, the event class it self should just be a plain old java object, nothing fancy, just a wrapper of data. 

```kotlin
// Model of a simple User 
data class User(
        var id: Long = 0L,
        var username: String = "",
        var email: String = ""
)

// Event to be raised when a new user signs up
class UserSignedUpEvent(user: User) : ApplicationEvent(user)
```

### Raising Events with ApplicationEventPublisher
Spring provides a convenient means to raise events with [ApplicationEventPubliser](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationEventPublisher.html). Inject this into any location where you need to raise events and call publish!

```kotlin
@Component
class UserService(
        var userRepo: UserRepository,
        var appEventPublisher: ApplicationEventPublisher){

    /**
      * Creates and saves a new user to the database, after succeeding it will
      * raise the UserSignedUpEvent for other interested components to react. 
      */ 
    fun register(username:String, email:String){
        var user = userRepo.save(User(username, email))
        applicationEventPublisher.publishEvent(UserSignedUpEvent(user))
    }
}
```

### Listening for Events
You can listen for events by either implementing the [ApplicationListener](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/ApplicationListener.html) interface or by annotation. As always don't forget that you class must be a Bean or else Spring will not have any control over it (here we are defining @Service).

Either approach is fine but the interface implementation makes it a bit more obvious and easier to search on. 

#### ApplicationListener Interface
```kotlin
@Service
class EmailService : ApplicationListener<UserSignedUpEvent> {
    override fun onApplicationEvent(event: UserSignedUpEvent) {
        var user = userSignedUpEvent.source as User
        // send out the email
    }
}
```
#### @EventListener Annotation
```kotlin
@Service
class EmailService {

    @EventListener
    fun sendWelcomeEmail(userSignedUpEvent: UserSignedUpEvent){
        var user = userSignedUpEvent.source as User
        // send out the email
    }

}
```

### Debugging Events
TODO.

## Async Events
TODO.

## Transactionl Events
TODO.

## Domain Events with Spring Data AbstractAggregateRoot
TODO.

## Spring Integration
We cover Spring Integration in another section but it's important to know that it has support for listening to Spring ApplicationEvents with a component called [ApplicationEventListeningMessageProducer](https://docs.spring.io/spring-integration/api/org/springframework/integration/event/inbound/ApplicationEventListeningMessageProducer.html).

* **Read:** [Spring ApplicationEvent Support](https://docs.spring.io/spring-integration/reference/html/applicationevent.html)
TODO - demonstrate.
