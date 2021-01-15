package org.acme.persistence

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.acme.domain.Timeslot
import javax.enterprise.context.ApplicationScoped

/*
É um repositório Panache. Ele é um helper em cima do hibernate, para deixar as coisas mais fáceis.
Ele já fornece vários métodos, é tipo um DAO pronto.
 */
// Annotation para poder usar minha classe na injeção de depência.
@ApplicationScoped
class TimeslotRepository : PanacheRepository<Timeslot> {

}