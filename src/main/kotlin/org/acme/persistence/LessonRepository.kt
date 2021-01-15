package org.acme.persistence

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.acme.domain.Lesson
import javax.enterprise.context.ApplicationScoped

// Annotation para poder usar minha classe na injeção de depência.
@ApplicationScoped
class LessonRepository:PanacheRepository<Lesson> {
}