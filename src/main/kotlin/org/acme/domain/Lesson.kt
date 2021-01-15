package org.acme.domain

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.variable.PlanningVariable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

/*
 * Optaplanner precisa saber o que ele precisa otimizar. Então ele precisa saber que ele pode
 * mudar a lesson (PlanningEntity) (ele não pode mudar o assunto e o professor). Mas ele pode
 * decidir para essas lesson, quando e onde (timeslot e room, com o @PlanningVariable).
 *
 * Então com o PlanningEntity dizemos que algo na classe vai mudar, que no caso são os atributos
 * anotados com PlanningVariable. Dessa forma, toda classe que pode mudar, precisa ter um planningvariable
 * e um planningentity.
 *
 * As planning variables começam com null, e terminam como non-null. Mas para poder decidir em
 * quais timeslots e em quais rooms ele pode colocar as lessons, ele precisa conhecer os timeslots e
 * rooms disponiveis. Essa lista de valores é informada usando o atributo valueRangeProviderRefs, que
 * recebe um identificador único. E de onde vamos pegar essa lista? Lá do timetable, pois ele que tem
 * essas listas, então é o lugar mais ideal para setar esses caras.
 */
@PlanningEntity
@Entity
class Lesson {

    @Id
    @GeneratedValue
    var id : Long? = null

    lateinit var subject: String
    lateinit var teacher: String
    lateinit var studentGroup: String


    @PlanningVariable(valueRangeProviderRefs = ["timeslotsRange"])
    // Precisamos associar as aulas á uma sala e à um timeslot
    // Eles vão ser salvos no banco e no inicio eles podem nascer como nulos, até o OptaPlanner
    // atribuir algo para elas.
    // Múltiplas lessons podem estar no mesmo timeslot
    @ManyToOne
    var timeslot: Timeslot? = null

    @PlanningVariable(valueRangeProviderRefs = ["roomRange"])
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