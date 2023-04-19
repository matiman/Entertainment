package com.bill.entertainment.unit

import com.bill.entertainment.dao.MovieRepository
import com.bill.entertainment.entity.Movie
import com.bill.entertainment.exception.MediaValidationException
import com.bill.entertainment.service.MovieService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDate

@RunWith(MockitoJUnitRunner::class)
class MovieServiceTest {
    @Mock
    private val movieRepository: MovieRepository? = null

    @InjectMocks
    private val movieService: MovieService? = null
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testDuplicateMovieCreation() {
        val movie = Movie()
        movie.title = "Existing movie"
        movie.releaseDate = LocalDate.now()
        val duplicateMovie = Movie()
        duplicateMovie.title = movie.title
        duplicateMovie.releaseDate = movie.releaseDate
        Mockito.`when`(movieRepository!!.findByTitleAndReleaseDate(movie.title, movie.releaseDate))
            .thenReturn(listOf(movie))
        Assertions.assertThrows(
            MediaValidationException::class.java, { movieService!!.create(duplicateMovie) },
            "Should throw MediaValidationException."
        )
    }
}