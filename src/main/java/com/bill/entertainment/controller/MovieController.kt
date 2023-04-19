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
            val movie = movieService!!.getById(id)
            ResponseEntity.ok(movie.toString())
        } catch (e: MediaNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createMovie(@RequestBody movie: @Valid Movie?): ResponseEntity<String?> {
        return try {
            val newMovie = movieService!!.create(movie)
            ResponseEntity.status(HttpStatus.CREATED).body(newMovie.toString())
        } catch (e: MediaValidationException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(movie.toString())
        }
    }

    @PutMapping(path = ["/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateMovie(@PathVariable id: Long?, @RequestBody movie: Movie?): ResponseEntity<String?> {
        return try {
            val updatedMovie = movieService!!.update(id, movie)
            ResponseEntity.ok(updatedMovie.toString())
        } catch (e: MediaNotFoundException) {
            ResponseEntity.badRequest().body(e.message)
        } catch (e: MediaValidationException) {
            ResponseEntity.badRequest().body(e.message)
        } catch (e: CreativesNotFoundException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteMovie(@PathVariable id: Long?): ResponseEntity<String?> {
        return try {
            movieService!!.delete(id)
            ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK.reasonPhrase)
        } catch (e: MediaNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        } catch (e: MediaDeletionException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

    @DeleteMapping("/actors/{id}")
    fun deleteActor(@PathVariable id: Long): ResponseEntity<String> {
        return try {
            movieService?.deleteActorNotInMovie(id)
            ResponseEntity.ok().body(ErrorMessages.SUCCESS)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

}