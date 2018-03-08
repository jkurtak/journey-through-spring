package com.jahnelgroup.demo.hello

import org.springframework.stereotype.Component

/**
 * Here we are defining the Italian implementation with the standard @Component stereotype.
 */
@Component
class ItalianService : HelloWorldService{

    override fun getHello() = "Ciao mondo!"

}