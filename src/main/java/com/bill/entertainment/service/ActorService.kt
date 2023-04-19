package com.bill.entertainment.service

import com.bill.entertainment.dao.ActorRepository
import com.bill.entertainment.entity.Actor
import com.bill.entertainment.exception.CreativesDeletionException
import com.bill.entertainment.exception.CreativesNotFoundException
import com.bill.entertainment.exception.CreativesValidationException
import com.bill.entertainment.utility.ErrorMessages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
open class ActorService : CreativesServiceImpl<Actor?, ActorRepository?>() {
    fun findById(id: Long?): Optional<Actor?> {
        return creativesRepository!!.findById(id)
    }

    @Throws(CreativesValidationException::class)
    override fun create(actor: Actor?): Actor? {
        if (!validate(actor)) throw CreativesValidationException(ErrorMessages.DUPLICATE_ACTOR)
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
        val actorOpt = creativesRepository?.findById(id)
        if (!id?.let { actorExists(it) }!!) {
            throw CreativesNotFoundException(ErrorMessages.ACTOR_NOT_FOUND)
        }
        try {
            if (actorOpt != null) {
                creativesRepository?.delete(actorOpt.get())
            }
        } catch (e: Exception) {
            throw CreativesDeletionException(ErrorMessages.CAN_NOT_DELETE_ACTOR_IN_MOVIE)
        }
    }

    fun actorExists(actorId: Long): Boolean {
        val actorOpt = creativesRepository?.findById(actorId)
        if (actorOpt != null) {
            return actorOpt.isPresent
        }
        return false;
    }
}