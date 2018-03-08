package com.jahnelgroup.demo

import com.jahnelgroup.demo.hello.HelloWorldService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class ConfigFile {

    @Bean
    fun croatianService() : HelloWorldService = object : HelloWorldService {
        override fun getHello() = "Pozdrav svijete"
    }

    @Profile("default", "prod")
    @Bean("greekService") // explicit bean name
    fun myGreekService() : HelloWorldService = object : HelloWorldService {
        override fun getHello() = "Γειά σου Κόσμε"
    }

}