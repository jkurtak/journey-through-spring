<table>
  <tr>
    <td><a href="../spring-kotlin"><< References</a></td>
    <td><a href="../spring-magic">Understanding the "Magic" of Spring >></a></td>
  </tr>
</table>

Getting Started
======

## Installing Tools and Knowing their Versions
As a professional software developer it’s important to know your tools. You should know exactly what tools are being used, where they are installed and what versions they are. It’s important that we install these tools in a uniform and systematic way. 

### Install SDKMAN!
SDKMAN is a great tool for installing the software needed for developing our Spring applications. It allows you to install multiple versions (called candidates) of software at the same time and allows you to easily switch between them. SDKMAN will persist data in your home directory ~/.sdkman. 

* **Install:** [SDKMAN](http://sdkman.io/install.html)
* **Read:** [SDKMAN usage](http://sdkman.io/usage.html)

### Install Java and Kotlin

We can build Spring with Java and Kotlin so install both using SDKMAN.

```bash
$ sdk install java
$ sdk install kotlin
```

### Install Maven and Gradle
Spring developers depend on a number of different tools to deliver software. It all begins with a build and dependency management tool. The two biggest names in the community now are [Maven](https://maven.apache.org/) and [Gradle](https://gradle.org/). Maven has been around longer with more example code and is really stable. Gradle is relatively new, adopted strongly by the Android community and is showing a lot more power than Maven as of late. Both will get the job done but the community is favoring Gradle. 

* **Read:** [Java Build Tools: How Dependency Management Works](https://zeroturnaround.com/rebellabs/java-build-tools-how-dependency-management-works-with-maven-gradle-and-ant-ivy/)

Install Maven and Gradle with SDKMAN.

```bash
$ sdk install maven
$ sdk install gradle
```

Follow these exercises and ignore the part about installing Maven or Gradle. 

* **Exercise:** [Building Java Projects with Maven](https://spring.io/guides/gs/maven/)
* **Exercise:** [Building Java Projects with Gradle](https://github.com/spring-guides/gs-gradle)
