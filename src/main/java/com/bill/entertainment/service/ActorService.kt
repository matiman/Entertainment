package com.bill.entertainment.service

import com.bill.entertainment.dao.ActorRepository
import com.bill.entertainment.entity.Actor
import com.bill.entertainment.exception.CreativesDeletionException
import com.bill.entertainment.exception.CreativesNotFoundException
import com.bill.entertainment.exception.CreativesValidationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
open class ActorService : CreativesServiceImpl<Actor?, ActorRepository?>() {
    @Autowired
    private val movieService: MovieService? = null
    fun findById(id: Long?): Optional<Actor?> {
        return creativesRepository!!.findById(id)
    }

    @Throws(CreativesValidationException::class)
    override fun create(actor: Actor?): Actor? {
        if (!validate(actor)) throw CreativesValidationException("Actor already exists")
        return creativesRepository!!.save(actor)
    }

    fun validate(actor: Actor?): Boolean {
        if (actor?.name != null) {
            if (creativesRepository!!.findByName(actor.name) == null) {
                return true
            }
        }
        return false
    }

    @Throws(CreativesNotFoundException::class, CreativesValidationException::class)
    override fun update(id: Long?, actor: Actor?): Actor? {
        val existingActor = getById(id)
        existingActor?.name=actor?.name
        return creativesRepository!!.save(existingActor)
    }

    @Throws(CreativesNotFoundException::class, CreativesDeletionException::class)
    override fun delete(id: Long?) {
        val actorOpt = creativesRepository!!.findById(id)
        if (actorOpt.isEmpty) {
            throw CreativesNotFoundException("Actor with ID $id not found")
        }
        val actor = actorOpt.get()
        val movies = movieService!!.getMoviesByActor(id)
        if (!movies!!.isEmpty()) {
            throw CreativesDeletionException("Actor with ID $id cannot be deleted because they are part of one more movies.")
        }
        creativesRepository!!.delete(actor)
    }
}