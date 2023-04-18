package com.bill.entertainment.service

import com.bill.entertainment.entity.Media
import com.bill.entertainment.exception.CreativesNotFoundException
import com.bill.entertainment.exception.MediaDeletionException
import com.bill.entertainment.exception.MediaNotFoundException
import com.bill.entertainment.exception.MediaValidationException

interface MediaService<T : Media?> {
    val all: List<T>?

    @Throws(MediaNotFoundException::class)
    fun getById(id: Long?): T

    @Throws(MediaValidationException::class, CreativesNotFoundException::class)
    fun create(media: T): T

    @Throws(MediaNotFoundException::class, MediaValidationException::class, CreativesNotFoundException::class)
    fun update(id: Long?, media: T): T

    @Throws(MediaNotFoundException::class, MediaDeletionException::class)
    fun delete(id: Long?)

    @Throws(MediaValidationException::class, CreativesNotFoundException::class)
    fun validateMedia(media: Media?, isNewMedia: Boolean)
}