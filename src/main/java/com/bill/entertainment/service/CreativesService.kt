package com.bill.entertainment.service

import com.bill.entertainment.entity.Creatives
import com.bill.entertainment.exception.CreativesNotFoundException
import com.bill.entertainment.exception.CreativesValidationException

interface CreativesService<T : Creatives?> {
    val all: List<T>?

    @Throws(CreativesNotFoundException::class)
    fun getById(id: Long?): T

    @Throws(CreativesValidationException::class)
    fun create(creatives: T): T

    @Throws(CreativesNotFoundException::class, CreativesValidationException::class)
    fun update(id: Long?, creatives: T): T

    @Throws(Exception::class)
    fun delete(id: Long?)
}