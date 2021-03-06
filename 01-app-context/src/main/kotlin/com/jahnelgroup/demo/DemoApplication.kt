package com.jahnelgroup.demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext

fun main(args: Array<String>) {

    /**
     * This will start the IoC Container and create a Bean of type ApplicationContext along with a
     * few others that you'll notice get printed out when this application runs.
     *
     * It will also scan and detect the PersonConfig configuration file to create the Beans
     * defined within that class as well.
      */
    runApplication<DemoApplication>(*args)

}

/**
 * DemoApplication is a @Component (drill down in the @SpringBootApplication to find it) therefore
 * an instance of this class is created when the IoC Container is started on line 10.
 */
@SpringBootApplication
class DemoApplication(

        /**
         * Spring will automatically Dependency Inject dependencies to create the DemoApplication object. Here
         * in the constructor we are declaring that we need a reference to ApplicationContext.
         */
        var appContext : ApplicationContext

) : ApplicationRunner {

    @Value("#{'Hello World'.concat('!')}")
    lateinit var hello: String

    override fun run(args: ApplicationArguments) {
        println("=".repeat(100))
        println(hello)
        println("========================\nPrinting Every Bean\n========================")
        appContext.beanDefinitionNames.forEach { println("Bean: $it") }

        println("=========================\nRetrieving Environment\n=========================")
        println(appContext.environment)

        println("=".repeat(100))
    }

}


