package com.bill.entertainment.service

import com.bill.entertainment.entity.Creatives
import com.bill.entertainment.exception.CreativesDeletionException
import com.bill.entertainment.exception.CreativesNotFoundException
import com.bill.entertainment.exception.CreativesValidationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository

abstract class CreativesServiceImpl<T : Creatives?, R : JpaRepository<T, Long?>?> : CreativesService<T> {
    @Autowired
    protected var creativesRepository: R? = null
    override val all: List<T>
        get() = creativesRepository!!.findAll()

    @Throws(CreativesNotFoundException::class)
    override fun getById(id: Long?): T {
        return creativesRepository!!.findById(id)
            .orElseThrow { CreativesNotFoundException("Creatives not found with id: $id") }
    }

    @Throws(CreativesValidationException::class)
    override fun create(creatives: T): T {
        validateCreatives(creatives)
        return creativesRepository!!.save(creatives)
    }

    @Throws(CreativesNotFoundException::class, CreativesValidationException::class)
    override fun update(id: Long?, creatives: T): T {
        val existingCreatives = getById(id)
        validateCreatives(existingCreatives)
        return creativesRepository!!.save(existingCreatives)
    }

    @Throws(Exception::class)
    override fun delete(id: Long?) {
        val existingCreatives = getById(id)
        try {
            creativesRepository!!.delete(existingCreatives)
        } catch (e: Exception) {
            throw CreativesDeletionException("Failed to delete creatives with id: $id")
        }
    }

    @Throws(CreativesValidationException::class)
    private fun validateCreatives(creatives: T) {
    }
}