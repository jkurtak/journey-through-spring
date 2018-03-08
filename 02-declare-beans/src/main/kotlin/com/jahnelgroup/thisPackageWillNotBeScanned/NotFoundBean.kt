package com.jahnelgroup.thisPackageWillNotBeScanned

import com.jahnelgroup.demo.hello.HelloWorldService
import org.springframework.stereotype.Component

/**
 * By default Component Scanning begins at the package-level where the main class is.
 *
 * In this example the DemoApplication main file is actually found in a different package branch so
 * this Bean will not be found!
 *
 * It's important to define your Beans at or under the package-level of your main file.
 *
 * Notice that the Beans in the hello package are found because they are beneath the main file.
 */
@Component
class NotFoundBean : HelloWorldService {
    override fun getHello(): String = "Oh no, this will never be found!"
}