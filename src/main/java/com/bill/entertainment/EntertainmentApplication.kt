package com.bill.entertainment

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories("com.bill.entertainment.dao")
@EntityScan(basePackages = ["com.bill.entertainment.entity"])
open class EntertainmentApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(EntertainmentApplication::class.java, *args)
        }
    }
}