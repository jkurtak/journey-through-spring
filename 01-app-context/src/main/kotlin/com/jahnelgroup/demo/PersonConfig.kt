package com.jahnelgroup.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PersonConfig{

    // By default beans get their names from the name of the function. In this case
    // both of these beans will be named steven and carrie respectively.

    @Bean fun steven() = Person("Steven Zgaljic")
    @Bean fun carrie() = Person("Carrie Zgaljic")

    // we are explicitly giving this bean a name of yuna

    @Bean("yuna")
    fun lauren() = Person("Lauren Zgaljic")

}