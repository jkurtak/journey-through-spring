package com.jahnelgroup.demo

import com.jahnelgroup.demo.hello.HelloWorldService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
    /**
     * Start the IoC Container
     */
    runApplication<DemoApplication>(*args)
}

@SpringBootApplication
class DemoApplication(

        /**
         * Spring will dependency inject beans through a sequence of qualifying properties which include
         * their names and class types. The italianService and greekService variable names here match their
         * corresponding bean names so they will be successfully found.
         */

        var italianService: HelloWorldService,
        var greekService: HelloWorldService,

        /**
         * In this case we are naming the variable "theFrenchService" which does not match the name of the
         * bean. We can use the @Qualifer annotation to help resolve this issue and find the bean.
         */

        @Qualifier("frenchService")
        var theFrenchService: HelloWorldService

) : ApplicationRunner {

    /**
     * It is also possible to dependency inject properties directly although constructors injection is favored when
     * you begin to Unit test your code.
     */
    @Autowired
    lateinit var croatianService: HelloWorldService

    /**
     * You can even ask Spring for a list of matching beans. In this default behavior Spring will inject
     * every HelloWorldService for us as a List.
     */
    @Autowired
    lateinit var allHelloServices : List<HelloWorldService>

    override fun run(args: ApplicationArguments?) {
        println("=".repeat(100))

        println("I can speak ${allHelloServices.size} languages!")

        println("French   : ${theFrenchService.getHello()}")
        println("Italian  : ${italianService.getHello()}")
        println("Croatian : ${croatianService.getHello()}")
        println("Greek    : ${greekService.getHello()}")

        println("=".repeat(100))
    }
}


