package com.jahnelgroup.demo.hello

import org.springframework.stereotype.Service
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

/**
 * Here we are defining the French implementation with the @Service stereotype. It will
 * register as a Bean in the ApplicationContext just like @Component.
 */
@Service
class FrenchService : HelloWorldService{

    override fun getHello() = "Bonjour le monde!"

    /**
     * Hooking into Spring Construction event for this Bean.
     */
    @PostConstruct
    fun init(){
        println(">> The FrenchService is being created. <<")
    }

    /**
     * Hooking into Spring Destruction event for this Bean.
     */
    @PreDestroy
    fun destroy(){
        println(">> The FrenchService is being destroyed. <<")
    }

}