package com.bill.entertainment.service

import com.bill.entertainment.dao.MovieRepository
import com.bill.entertainment.entity.Media
import com.bill.entertainment.entity.Movie
import com.bill.entertainment.exception.CreativesNotFoundException
import com.bill.entertainment.exception.MediaDeletionException
import com.bill.entertainment.exception.MediaNotFoundException
import com.bill.entertainment.exception.MediaValidationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class MovieService : MediaServiceImpl<Movie?, MovieRepository?>() {
    @Autowired
    private val actorService: ActorService? = null
    fun getMoviesByActor(actorId: Long?): List<Movie?>? {
        return mediaRepository!!.findMoviesByActorId(actorId)
    }

    @Throws(MediaValidationException::class, IllegalArgumentException::class, CreativesNotFoundException::class)
    override fun create(movie: Movie?): Movie? {
        return try {
            validateMedia(movie, true)
            mediaRepository!!.save(movie)
        } catch (e: MediaValidationException) {
            throw MediaValidationException(e.message)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(e.message)
        } catch (e: CreativesNotFoundException) {
            throw CreativesNotFoundException(e.message)
        }
    }

    @Throws(MediaNotFoundException::class, MediaValidationException::class, CreativesNotFoundException::class)
    override fun update(id: Long?, updatedMovie: Movie?): Movie? {
        val movie = mediaRepository!!.findById(id)
            .orElseThrow { MediaNotFoundException("Movie with id $id not found") }!!
        movie.title= updatedMovie?.title
        movie.releaseDate= updatedMovie?.releaseDate
        movie.actors= updatedMovie?.actors?.toSet() ?: emptySet()
        //newActorSet?.toSet() ?: movie.actors

         movie.actors?.forEach { actor ->
             actorService!!.validate(actor)
        }
        validateMedia(movie, false)
        return mediaRepository!!.save(movie)
    }

    @Throws(MediaNotFoundException::class, MediaDeletionException::class)
    override fun delete(id: Long?) {
        val movie = mediaRepository!!.findById(id)
            .orElseThrow { MediaNotFoundException("Movie with id $id not found") }!!
        try {
            mediaRepository!!.delete(movie)
        } catch (e: Exception) {
            throw MediaDeletionException("Failed to delete movie id: $id", e)
        }
    }


    @Throws(MediaValidationException::class, CreativesNotFoundException::class)
    override fun validateMedia(media: Media?, isNewMedia: Boolean) {
        require(media is Movie) { "Media object is not an instance of Movie" }
        val movie = media
        if (isNewMedia && !mediaRepository!!.findByTitleAndReleaseDate(movie.title, movie.releaseDate)!!
                .isEmpty()
        ) {
            throw MediaValidationException("Movie with title " + movie.title + " and release date " + movie.releaseDate + " already exists")
        }
        if (movie.actors.isEmpty()) {
            throw MediaValidationException("Movie must have at least an actor.")
        }
        for (actor in movie.actors) {
            if (actorService!!.findById(actor.id).isEmpty) throw CreativesNotFoundException("At least one of the actors isn't present in our database")
        }
    }

}