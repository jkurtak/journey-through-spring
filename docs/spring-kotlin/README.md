<table><tr><td><a href="https://github.com/JahnelGroup/journey-through-spring">Table of Contents</a></td></tr></table>

What is Kotlin?
======

Kotlin started with the Android community and solves many problems that exist in Java. It does an excellent job at inheriting the best features of various languages and eliminates a significant amount of the verbosity found in Java. It’s powerful features and 100% interoperability with Java make a very compelling argument as a language replacement.

> *Kotlin is a general purpose, open source, statically typed “pragmatic” programming language for the JVM and Android that combines object-oriented and functional programming features. It is focused on interoperability, safety, clarity, and tooling support. Versions of Kotlin for JavaScript (ECMAScript 5.1) and native code (using LLVM) are in the works. - *[What is Kotlin](https://www.infoworld.com/article/3224868/java/what-is-kotlin-the-java-alternative-explained.html)**

* **Read:** [What is Kotlin? The Java alternative explained](https://www.infoworld.com/article/3224868/java/what-is-kotlin-the-java-alternative-explained.html)
* **Read:** [Using Kotlin for Server-side Development](https://kotlinlang.org/docs/reference/server-overview.html)

This journey will focus primarily on Spring Boot and the example code will be written in Kotlin.

* **Read:** [Comparison to Java Programming Language](https://kotlinlang.org/docs/reference/comparison-to-java.html)
* **Read:** [Introducing Kotlin support in Spring Framework 5.0](https://spring.io/blog/2017/01/04/introducing-kotlin-support-in-spring-framework-5-0)

## Main Differences from Java
If you're new to Kotlin here are a few main differences from Java that will help you follow along in this Journey.

### Semicolons
Semicolons are optional.

**Java**
```java
System.out.println("Hello, World!");      
```    
**Kotlin**
```kotlin
println("Hello, World!")
``` 

### Type Inference
Both Java and Kotlin are considered strict static languages but Kotlin has type inference. This means if the compiler can determine the type of a reference without a doubt then you can omit it. Also the type is defined on the right side of the variable name instead of the left.

**Java**
```java
Integer num = 1;
String str = "Hello";
```    
**Kotlin**
```kotlin
var num = 1
var str: String = "Hello"
```

### Instantiating Objects
In Java we use the **new** keyword to instantiate an Object, but that does not exist in Kotlin so we simply omit it. 

**Java**
```java
User user = new User(1, "Steven");
```    
**Kotlin**
```kotlin
var user = User(1, "Steven")
```

### Declaring Classes
TODO.... 
