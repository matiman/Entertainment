package com.bill.entertainment.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice
class CustomErrorAdvice {
    init {
        println("CustomErrorAdvice instantiated")
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handle404(e: NoHandlerFoundException?): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found")
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(e: HttpMessageNotReadableException?): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON syntax")
    }
}