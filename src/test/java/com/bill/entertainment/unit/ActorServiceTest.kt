package com.bill.entertainment.unit

import com.bill.entertainment.dao.ActorRepository
import com.bill.entertainment.entity.Actor
import com.bill.entertainment.entity.Movie
import com.bill.entertainment.exception.CreativesDeletionException
import com.bill.entertainment.exception.CreativesValidationException
import com.bill.entertainment.service.ActorService
import com.bill.entertainment.service.MovieService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDate
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ActorServiceTest {
    @InjectMocks
    private val actorService: ActorService? = null

    @Mock
    private val movieService: MovieService? = null

    @Mock
    private val repository: ActorRepository? = null
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testCreateActorWithExistingName() {
        val existingActor = Actor()
        existingActor.id = 1L
        existingActor.name = "Emma Lincoln"
        Mockito.`when`(repository!!.findByName("Emma Lincoln")).thenReturn(existingActor)
        val newActor = Actor()
        newActor.name = "Emma Lincoln"
        Assertions.assertThrows(
            CreativesValidationException::class.java, { actorService!!.create(newActor) },
            "Should throw CreativesValidationException."
        )
    }

//    @Test
//    fun testDeleteActorInMovie() {
//        val actor = Actor()
//        actor.id = 1L
//        actor.name = "John Doe"
//        val movie = Movie()
//        movie.id = 1L
//        movie.title = "Test Movie"
//        movie.releaseDate = LocalDate.of(2023, 4, 17)
//        movie.actors = setOf(actor)
//        Mockito.`when`(repository!!.findById(1L)).thenReturn(Optional.ofNullable(actor).map { it })
//        Mockito.`when`(movieService!!.getMoviesByActor(ArgumentMatchers.anyLong())).thenReturn(listOf(movie))
//        Assertions.assertThrows(CreativesDeletionException::class.java) { actorService!!.delete(1L) }
//        Mockito.verify(repository, Mockito.never()).delete(actor)
//    }
}