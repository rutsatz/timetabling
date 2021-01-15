package org.acme.constraints

import org.acme.domain.Lesson
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import org.optaplanner.core.api.score.stream.Constraint
import org.optaplanner.core.api.score.stream.ConstraintFactory
import org.optaplanner.core.api.score.stream.ConstraintProvider
import org.optaplanner.core.api.score.stream.Joiners

/*
Definimos as nossas contraints para o time table, como por exemplo, um professor não pode ter
duas aulas ao mesmo tempo e um grupo de estudantes não pode ter duas aulas ao mesmo tempo.

Implementa a interface ConstraintProvider para dizer as contraints ao optaplanner.
 */
class TimeTableConstraintProvider : ConstraintProvider {

    override fun defineConstraints(constraintFactory: ConstraintFactory): Array<Constraint> {
        return arrayOf(
            roomConflict(constraintFactory),
            teacherConflict(constraintFactory),
            studentGroupConflict(constraintFactory)
        )
    }

    /*
    Não permite duas lessons na mesma sala ao mesmo tempo.
    Mas somente isso ainda não me dá uma escala válida, pois ainda posso um professor dando
    duas aulas ao mesmo tempo. Então tenho olhar para o conflito de professor
     */
    private fun teacherConflict(constraintFactory: ConstraintFactory): Constraint {
        // Vou olhar para uma Lesson
        return constraintFactory.from(Lesson::class.java)
                // Quando temos uma lesson e a juntamos com outra lesson
            .join(Lesson::class.java,
                // As duas lessons tem o mesmo timeslot
                Joiners.equal(Lesson::timeslot),
                // E as duas lessons tem a mesma sala
                Joiners.equal(Lesson::room)
            )
            // Agora temos duas lessons e temos um conflito
            // Então toda vez que o OptaPlanner atribuir duas lessons na mesma sala
            // no mesmo timeslot, então vamos penalizar essa solução e dizer
            // que viola uma hard constraint, ou seja, não é uma solução válida.
            .penalize("room conflict", HardSoftScore.ONE_HARD)
    }

    /*
    Mesma ideia da constraint anterior, só que agora, ao invés de olhar o timeslot e a sala,
    eu olho o timeslot e o professor. Ou seja, se o mesmo professor estiver no mesmo timeslot, preciso
    penalizar a solução, pois não é uma solução válida.

    porém mesmo com essas duas contraints, ainda tenho um problema que posso ter dois grupos de estudantes ocupando
    a mesma sala em horários diferentes.
     */
    private fun roomConflict(constraintFactory: ConstraintFactory): Constraint {
        // Vou olhar para uma Lesson
        return constraintFactory.from(Lesson::class.java)
            // Quando temos uma lesson e a juntamos com outra lesson
            .join(Lesson::class.java,
                // As duas lessons tem o mesmo timeslot
                Joiners.equal(Lesson::timeslot),
                // E as duas lessons tem o mesmo professor
                Joiners.equal(Lesson::teacher)
            )
            // Agora temos duas lessons e temos um conflito
            // Então toda vez que o OptaPlanner atribuir dois professores na mesma sala
            // no mesmo timeslot, então vamos penalizar essa solução e dizer
            // que viola uma hard constraint, ou seja, não é uma solução válida.
            .penalize("teacher conflict", HardSoftScore.ONE_HARD)
    }

    /*
    Agora devemos ter nosso primeira resultado viável, ou seja, nenhuma hard contraint violada e nenhuma pessoa
    ocupando dois espaços ao mesmo tempo.
     */
    private fun studentGroupConflict(constraintFactory: ConstraintFactory): Constraint {
        // Vou olhar para uma Lesson
        return constraintFactory.from(Lesson::class.java)
            // Quando temos uma lesson e a juntamos com outra lesson
            .join(Lesson::class.java,
                // As duas lessons tem o mesmo timeslot
                Joiners.equal(Lesson::timeslot),
                // E as duas lessons tem o mesmo grupo de estudantes
                Joiners.equal(Lesson::studentGroup)
            )
            // Agora temos duas lessons e temos um conflito
            // Então toda vez que o OptaPlanner atribuir dois professores na mesma sala
            // no mesmo timeslot, então vamos penalizar essa solução e dizer
            // que viola uma hard constraint, ou seja, não é uma solução válida.
            .penalize("studentGroup conflict", HardSoftScore.ONE_HARD)
    }

}