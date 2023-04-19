package com.bill.entertainment.controller

import com.bill.entertainment.entity.Actor
import com.bill.entertainment.exception.CreativesDeletionException
import com.bill.entertainment.exception.CreativesNotFoundException
import com.bill.entertainment.exception.CreativesValidationException
import com.bill.entertainment.service.ActorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/actors")
class ActorController {
    @Autowired
    private val actorService: ActorService? = null

    @get:GetMapping
    val allActors: ResponseEntity<List<Actor>>
        get() = try {
            val actors = actorService!!.all as? List<Actor> ?: emptyList()
            ResponseEntity.ok(actors)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

    @GetMapping("/{id}")
    fun getActorById(@PathVariable id: Long?): ResponseEntity<String?> {
        return try {
            val actor = actorService!!.getById(id)
            ResponseEntity.ok(actor?.toString())
        } catch (e: CreativesNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createActor(@RequestBody newActor: Actor?): ResponseEntity<String?> {
        return try {
            val actor = actorService!!.create(newActor)
            ResponseEntity.status(HttpStatus.CREATED).body(actor.toString())
        } catch (e: CreativesValidationException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

    @PutMapping(path = ["/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateActor(@PathVariable id: Long?, @RequestBody actor: Actor?): ResponseEntity<String?> {
        return try {
            val updatedActor = actorService!!.update(id, actor)
            ResponseEntity.ok(updatedActor.toString())
        } catch (e: CreativesNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        } catch (e: CreativesValidationException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteActor(@PathVariable id: Long?): ResponseEntity<String?> {
        return try {
            actorService!!.delete(id)
            ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK.reasonPhrase)
        } catch (e: CreativesNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        } catch (e: CreativesDeletionException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
}