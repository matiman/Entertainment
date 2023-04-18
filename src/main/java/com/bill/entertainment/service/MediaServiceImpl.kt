package com.bill.entertainment.service

import com.bill.entertainment.entity.Media
import com.bill.entertainment.exception.MediaNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository

abstract class MediaServiceImpl<T : Media?, R : JpaRepository<T, Long?>?> : MediaService<T> {
    @Autowired
    protected var mediaRepository: R? = null
    override val all: List<T>
        get() = mediaRepository!!.findAll()

    @Throws(MediaNotFoundException::class)
    override fun getById(id: Long?): T {
        return mediaRepository!!.findById(id)
            .orElseThrow { MediaNotFoundException("Media id not found: $id") }
    }
}