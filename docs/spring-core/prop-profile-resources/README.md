<table><tr><td><a href="https://github.com/JahnelGroup/journey-through-spring">Table of Contents</a></td></tr></table>

Properties, Profiles and Resources
======

## Externalized Configuration
You’ll likely need to change the behavior of your application based on a number of things. Spring has a lot features around this called Externalized Configuration - the practice of placing configuration in property files as opposed to hard-coded in your program. Spring will respect a very specific hierarchical order of properties such that you can override properties when required.  

* **Read:** [Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html)
* **Read:** [Appendix A. Common application properties](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)

Property files are typically placed in **src/main/resources** as either *.properties* or *.yml* files. **application.properties** and **application.yml** are specially named properties that Spring will look for to load automatically when your application starts. There are profile-specific variants of these property files as well and are described below. 

## Profiles
Spring has the concept of a Profile which is basically a special type of property that you can use to *tag* your components to a specific environment. Let’s say you have a service that should send an EMail out when certain events happen in your system. This may only be relevant in an environment like UAT and PROD but not in DEV. You could define two types of MailService, one to actually send the EMail out and the other to just print it to the console.

* **Read:** [Profiles](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html)

```kotlin
/** 
  * Only one of the two Beans defined in this class will be loaded in any given environment. 
  */ 
@Configuration
class MyConfig {

  // Use the real EMailService in UAT and PROD
  @Bean
  @Profile(“UAT”, “PROD”)
  fun emailService(): MailService = EMailService()

  // Use the console based service for DEV
  @Bean
  @Profile(“DEV”)
  fun consoleMailService(): MailService = ConsoleMailService()

}
```

There are several ways to make a Profile "active", one such way is to pass the **spring.profiles.active** JVM argument.

```bash
$ java -jar -Dspring.profiles.active=PROD app.jar
```

Profiles work very nicely with the properties mentioned earlier. Spring will load properties based on a naming scheme with the Profile name in the format application-{profile}.properties (i..e, application-PROD.properties). Profile specific properties will override the default properties. 

A common example of this is externalizing database connection property for each envirionment.

* src/main/resources/application.properties
  * spring.datasource.url=jdbc:mysql://localhost:3306/mydb
* src/main/resources/application-DEV.properties
  * spring.datasource.url=jdbc:mysql://dev-database.app.com:3306/mydb
* src/main/resources/application-PROD.properties
  * spring.datasource.url=jdbc:mysql://prod-database.app.com:3306/mydb
