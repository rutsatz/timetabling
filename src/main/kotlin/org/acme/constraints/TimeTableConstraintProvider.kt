package org.acme.constraints

import org.optaplanner.core.api.score.stream.Constraint
import org.optaplanner.core.api.score.stream.ConstraintFactory
import org.optaplanner.core.api.score.stream.ConstraintProvider

/*
Definimos as nossas contraints para o time table, como por exemplo, um professor não pode ter
duas aulas ao mesmo tempo e um grupo de estudantes não pode ter duas aulas ao mesmo tempo.

Implementa a interface ConstraintProvider para dizer as contraints ao optaplanner.
 */
class TimeTableConstraintProvider : ConstraintProvider {

    override fun defineConstraints(constraintFactory: ConstraintFactory?): Array<Constraint> {
        return arrayOf()
    }


}