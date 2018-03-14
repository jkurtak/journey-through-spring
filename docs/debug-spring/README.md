<table><tr><td><a href="https://github.com/JahnelGroup/journey-through-spring">Table of Contents</a></td></tr></table>

Debugging
======
You just wrote an awesome program, fired it up and ugh-o...it doesn't work like you expect! Writing code is *easily* figuring out what's wrong with broken code is *really hard*. You have a couple of options.

## Print Statements (DO NOT DO THIS)
Yeah that's right, the good old print statement. It's the worst way to debug a problem but it sure is a technique and we've all been there so why not cover it. Doing this has so many problems but the main reason I feel is that you're actually editing source-code for debugging and you'll likely end up just leaving it there to flood the logs in prod. 

**Do not do this.**

```kotlin
println("condition = $condition")
if( condition ) {
  println("Yep, condition sure is true")
}else{
  println("No way, condition is false!")
}
```

## Logger
Instead of print statements you should opt for using a [Logger](https://en.wikipedia.org/wiki/Java_logging_framework).

* **Read:** [Java Logging Basics](https://www.loggly.com/ultimate-guide/java-logging-basics/)

## Debugger

### Local

### Remote
