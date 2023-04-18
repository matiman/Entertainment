package com.bill.entertainment.dao

import com.bill.entertainment.entity.Actor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActorRepository : JpaRepository<Actor?, Long?> {
    fun findByName(name: String?): Actor?
}