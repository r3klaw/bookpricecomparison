package com.calvinlow.bookpricecomparison

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@ComponentScan
class BookPriceComparisonApplication

fun main(args: Array<String>) {
    SpringApplication.run(BookPriceComparisonApplication::class.java, *args)
}
