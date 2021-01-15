package org.acme.persistence

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.acme.domain.Room
import javax.enterprise.context.ApplicationScoped

// Annotation para poder usar minha classe na injeção de depência.
@ApplicationScoped
class RoomRepository: PanacheRepository<Room> {
}