package com.bill.entertainment.entity

import jakarta.annotation.Nonnull
import jakarta.persistence.*
import lombok.Data
import lombok.Getter

@Entity
@Table(name = "actors")
@Data
class Actor : Creatives() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, unique = true)
    @Nonnull
    var name: String? = null

    override fun toString(): String {
        return "Actor(id=$id, name=$name)"
    }
}