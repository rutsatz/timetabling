package org.acme.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Lesson {

    @Id
    @GeneratedValue
    var id : Long? = null

    lateinit var subject: String
    lateinit var teacher: String
    lateinit var studentGroup: String

    // Precisamos associar as aulas á uma sala e à um timeslot
    // Eles vão ser salvos no banco e no inicio eles podem nascer como nulos, até o OptaPlanner
    // atribuir algo para elas.
    // Múltiplas lessons podem estar no mesmo timeslot
    @ManyToOne
    var timeslot: Timeslot? = null
    // Múltiplas lessons podem estar na mesma sala, contanto que não esteja ao mesmo tempo no mesmo timeslot
    // e na mesma sala. E mesmo se estiverem, ainda precisamos representá-los no banco de dados
    // (apesar de não ser algo válido para o planejamento, pois provavelmente vai quebrar alguma constraint)
    @ManyToOne
    var room: Room? = null

    // Construtor para testes
    constructor(subject: String, teacher: String, studentGroup: String) {
        this.subject = subject
        this.teacher = teacher
        this.studentGroup = studentGroup
    }

    // construtor para a JPA e tbm para o OptaPlanner, pois ele vai precisar criar as lessons.
    constructor()


}