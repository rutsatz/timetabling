package org.acme.domain

/*
TimeTable representa o dataset da escola (O problema de uma escola).
Por exemplo, tenho x disciplinas, x professores, x alunos que precisam
ser organizados em x timeslots. Se eu tivesse várias escolas por exemplo, eu teria um TimeTable para cada escola.

E esse é um problema que precisa ser resolvido por completo. Ele não pode ser quebrado para ser resolvido.
(Você poderia particionar, mas não é uma boa idéia.)
 */
class TimeTable {

    // Queremos uma lista de todos os possíveis timeslots.
    lateinit var timeslotList: List<Timeslot>

    // Lista de todas as salas da escola
    lateinit var roomList: List<Room>

    // Lista de todas as disciplinas da escola
    lateinit var lessonList: List<Lesson>

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