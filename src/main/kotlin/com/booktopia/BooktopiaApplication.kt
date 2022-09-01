package com.booktopia

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BooktopiaApplication

fun main(args: Array<String>) {
	runApplication<BooktopiaApplication>(*args)
}
