package com.bill.entertainment.dao

import com.bill.entertainment.entity.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface MovieRepository : JpaRepository<Movie?, Long?> {
    @Query("SELECT m FROM Movie m JOIN m.actors a WHERE a.id = :actorId")
    fun findMoviesByActorId(@Param("actorId") actorId: Long?): List<Movie?>?

    @Query("SELECT m FROM Movie m WHERE m.title = :title AND m.releaseDate = :releaseDate")
    fun findByTitleAndReleaseDate(
        @Param("title") title: String?,
        @Param("releaseDate") releaseDate: LocalDate?
    ): List<Movie?>?
}