package org.acme

import org.acme.domain.Lesson
import org.acme.domain.TimeTable
import org.acme.persistence.LessonRepository
import org.acme.persistence.RoomRepository
import org.acme.persistence.TimeslotRepository
import org.optaplanner.core.api.solver.SolverManager
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("timeTable")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class TimeTableResource {

    // Injeta meu repositório
    @Inject
    lateinit var timeslotRepository: TimeslotRepository

    @Inject
    lateinit var roomRepository: RoomRepository

    @Inject
    lateinit var lessonRepository: LessonRepository

    // SolverManager é o que usamos para resolver um problema. Nesse caso, queremos resolver o TimeTable,
    // mas poderíamos passar múltiplos problemas para ele. Ele tem dois tipo, o primeiro é o tipo
    // de problema que queremos resolver (TimeTable, anotado com PlanningSolution). E o segundo é um
    // id único, para que possamos diferenciar entre as diferentes soluções que ele vai criar.
    @Inject
    lateinit var solverManager: SolverManager<TimeTable, Long>

    @GET
    @Transactional
    fun getTimeTable(): TimeTable {
        return TimeTable(
            timeslotRepository.listAll(),
            roomRepository.listAll(),
            lessonRepository.listAll()
        )
    }

    @POST
    @Path("/solve")
    fun solve() {
        // Assim que o optaplanner for achando soluções melhores, já queremos ver elas na tela
        // (Não queremos esperar até o fim para poder ver a solução). Para isso usamos o solveAndListen,
        // que resolve o problema e tbm permite eu ver as soluções intermediárias.
        // Nesse caso, usamos um método que tem 3 parametros. O primeiro é o id do problema,
        // nesse caso deixamos fixo como 1, pois temos somente uma escola.
        // Os dois outros parâmetros são duas funcções. A primeira função é para encontrar
        // o timeTable que quero resolver. (Ele passa por parâmetro o id do problema que user ao criar o solverManager).
        // O segundo método é o que salva a solução.
        solverManager.solveAndListen(
            1L,
            this::findById,
            this::save
        )
    }

    // Nesse caso, como temos somente um problema, podemos retornar direto o getTimeTable.
    fun findById(jobId: Long): TimeTable {
        return getTimeTable()
    }

    /*
    Essa função vai ser chamada toda vez que o optaplanner achar uma nova melhor solução.
    Nesse caso, vamos querer restauras as lessons e a atribuição do timeslot.
     */
    @Transactional
    fun save(timeTable: TimeTable) {
        for (lesson in timeTable.lessonList) {
            // Nesse caso, podemos fazer o null check (!!), pois sabemos que quando o optaplanner nos entregar
            // o timetable, ele já vai estar com os ids preenchidos. Mas não fazer isso em produção, é bom tratar.
            // Ele vai nos passar o timeTable que passamos pra ele na primeira função (findById), por isso
            // nesse caso podemos fazer isso, temos certeza que não estará null.
            // Buscamos no repository, pois nesse ponto aqui, as entidades da jpa não estão mais attached, então
            // preciso atribuir novamente. Então busco novamente a mesma lesson do banco, para torna la gerenciavel
            // novamente pela jpa e atribuir novamente os timeslots e lessons.
            val attachedLesson: Lesson = lessonRepository.findById(lesson.id!!)!!
            // Vinculamos a nossa lesson do banco com o objeto manipulado pelo optaplanner,
            // para que sempre que ele fizer uma alteração, isso seja refletido no
            // banco de dados. Esses são os nossos dois atributos marcados com @PlanningVariable,
            // dizendo pro optaplanner otimizar eles, então ele vai ficar mudando isso. E quando tiver uma
            // nova melhor solução, ele vai salvar no banco, chamando esse método.
            attachedLesson.timeslot = lesson.timeslot
            attachedLesson.room = lesson.room
        }
    }

}