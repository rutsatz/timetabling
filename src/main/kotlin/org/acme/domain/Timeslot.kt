package org.acme.domain

import java.time.DayOfWeek
import java.time.LocalTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Timeslot {

    @Id
    @GeneratedValue
    var id : Long? = null

    // lateinit pq se vier do banco, o hibernate vai popular para nós.
    // Se precisarmos testar, podemos criar um construtor para popular.
    lateinit var dayOfWeek: DayOfWeek

    lateinit var startTime: LocalTime
    lateinit var endTime: LocalTime

    // Construtor usado pelo hibernate.
    constructor()

    // Construtor usado para criar objetos para teste.
    constructor(dayOfWeek: DayOfWeek, startTime: LocalTime, endTime: LocalTime) {
        this.dayOfWeek = dayOfWeek
        this.startTime = startTime
        this.endTime = endTime
    }


}