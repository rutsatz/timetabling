package org.acme.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Room {

    @Id
    @GeneratedValue
    var id : Long? = null

    lateinit var name: String

    // construtor para usarmos nos testes
    constructor(name: String) {
        this.name = name
    }

    // construtor para o hibernate
    constructor()

}