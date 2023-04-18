package com.bill.entertainment.controller

import com.bill.entertainment.entity.Movie
import com.bill.entertainment.exception.CreativesNotFoundException
import com.bill.entertainment.exception.MediaDeletionException
import com.bill.entertainment.exception.MediaNotFoundException
import com.bill.entertainment.exception.MediaValidationException
import com.bill.entertainment.service.MovieService
import com.bill.entertainment.utility.ErrorMessages
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/movies")
class MovieController {
    @Autowired
    private val movieService: MovieService? = null

    @get:GetMapping
    val allMovies: ResponseEntity<List<Movie>>
        get() = try {
            val movies = movieService!!.all as? List<Movie> ?: emptyList()
            ResponseEntity.ok(movies)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

    @GetMapping("/{id}")
    fun getMovieById(@PathVariable(value = "id") id: Long?): ResponseEntity<String?> {
        return try {
            movieService!!.getById(id)
            ResponseEntity.ok(ErrorMessages.SUCCESS)
        } catch (e: MediaNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessages.MOVIE_NOT_FOUND)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessages.BAD_REQUEST)
        }
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createMovie(@RequestBody movie: @Valid Movie?): ResponseEntity<String?> {
        return try {
            movieService!!.create(movie)
            ResponseEntity.status(HttpStatus.CREATED).body(ErrorMessages.SUCCESS)
        } catch (e: MediaValidationException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessages.INVALID_INPUT)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessages.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessages.BAD_REQUEST)
        }
    }

    @PutMapping(path = ["/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateMovie(@PathVariable id: Long?, @RequestBody movie: Movie?): ResponseEntity<String?> {
        return try {
             movieService!!.update(id, movie)
            ResponseEntity.ok(ErrorMessages.SUCCESS)
        } catch (e: MediaNotFoundException) {
            ResponseEntity.badRequest().body(ErrorMessages.MOVIE_NOT_FOUND)
        } catch (e: MediaValidationException) {
            ResponseEntity.badRequest().body(ErrorMessages.INVALID_INPUT)
        } catch (e: CreativesNotFoundException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessages.ACTOR_NOT_FOUND)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessages.BAD_REQUEST)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteMovie(@PathVariable id: Long?): ResponseEntity<String?> {
        return try {
            movieService!!.delete(id)
            ResponseEntity.ok(ErrorMessages.SUCCESS)
        } catch (e: MediaNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessages.MOVIE_NOT_FOUND)
        } catch (e: MediaDeletionException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessages.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessages.BAD_REQUEST)
        }
    }
}