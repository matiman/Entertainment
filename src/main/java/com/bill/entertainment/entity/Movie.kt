package com.bill.entertainment.entity

import jakarta.annotation.Nonnull
import jakarta.persistence.*
import lombok.Data
import java.time.LocalDate

@Entity
@Data
@Table(name = "movies", uniqueConstraints = [UniqueConstraint(columnNames = ["title", "releaseDate"])])
class Movie : Media() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    @Nonnull
    var title: String? = null

    @Column(nullable = false)
    @Nonnull
    var releaseDate: LocalDate? = null

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "movie_actor",
        joinColumns = [JoinColumn(name = "movie_id")],
        inverseJoinColumns = [JoinColumn(name = "actor_id")],
        uniqueConstraints = [UniqueConstraint(columnNames = ["movie_id", "actor_id"])]
    )
    /*

    */
    var actors: Set<Actor> = HashSet()

    override fun toString(): String {
        return "Movie(id=$id, title=$title, releaseDate=$releaseDate, actors=$actors)"
    }

}