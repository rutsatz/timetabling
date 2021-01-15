package org.acme.domain

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty
import org.optaplanner.core.api.domain.solution.PlanningScore
import org.optaplanner.core.api.domain.solution.PlanningSolution
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore

/*
TimeTable representa o dataset da escola (O problema de uma escola).
Por exemplo, tenho x disciplinas, x professores, x alunos que precisam
ser organizados em x timeslots. Se eu tivesse várias escolas por exemplo, eu teria um TimeTable para cada escola.

E esse é um problema que precisa ser resolvido por completo. Ele não pode ser quebrado para ser resolvido.
(Você poderia particionar, mas não é uma boa idéia.)

PlanningSolution - Uma vez que as as variáveis estão atribuidas, o timetable passa de um problema
para uma possível solução. Então digo que ele tbm é um planning solution.
 */
@PlanningSolution
class TimeTable {

    // Queremos uma lista de todos os possíveis timeslots.
    // Passamos a lista de todos os timeslots disponiveis. Ele vai selecionar esses valores
    // para montar a solução. Esse é o provider que vai ser usado no @PlanningVariable que tiver
    // o mesmo id.
    @ValueRangeProvider(id = "timeslotsRange")
    lateinit var timeslotList: List<Timeslot>

    // Lista de todas as salas da escola
    @ValueRangeProvider(id = "roomRange")
    lateinit var roomList: List<Room>

    // Lista de todas as disciplinas da escola
    // E ele tbm não sabe quais as lesson que tenho disponiveis para otimizar. Então tenho que passar isso pra ele.
    // PlanningEntityCollectionProperty digo para ele que esse é o conjunto de lessons que ele precisa atribuir.
    // ou seja, para cada lesson da lista, ele vai precisar resolver as planning variables, ou seja, encontrar
    // os timeslots e rooms.
    @PlanningEntityCollectionProperty
    lateinit var lessonList: List<Lesson>

    // O Optaplanner vai precisar calcular o score para essa possível solução, para saber o quão boa
    // ela é. Se é uma solução viável ou não. Para isso, vamos usar o HardSoftScore, que diz que as
    // hard contraints precisam ser atendidas completamente e as soft contraints precisam ser atendidas
    // o máximo possível. E iniciamos como null, pois no inicio não sabemos o score, e o optaplanner vai
    // calcular e atribuir ele para nós.
    @PlanningScore
    var score: HardSoftScore? = null

    // construtor para testes
    constructor(timeslotList: List<Timeslot>, roomList: List<Room>, lessonList: List<Lesson>) {
        this.timeslotList = timeslotList
        this.roomList = roomList
        this.lessonList = lessonList
    }

    // Construtor que o OptaPlanner vai precisar. O objeto não vai ser salvo no banco, então não vai ser
    // usado pelo hibernate.
    constructor()


}