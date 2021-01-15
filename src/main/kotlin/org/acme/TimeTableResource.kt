package org.acme

import org.acme.domain.TimeTable
import org.acme.persistence.LessonRepository
import org.acme.persistence.RoomRepository
import org.acme.persistence.TimeslotRepository
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("timeTable")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class TimeTableResource{

    // Injeta meu repositório
    @Inject
    lateinit var timeslotRepository: TimeslotRepository

    @Inject
    lateinit var roomRepository: RoomRepository

    @Inject
    lateinit var lessonRepository: LessonRepository

    @GET
    fun getTimeTable(): TimeTable {
        return TimeTable(timeslotRepository.listAll(),
            roomRepository.listAll(),
            lessonRepository.listAll())
    }

}