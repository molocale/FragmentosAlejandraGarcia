package com.example.actividadfragmentosalejandragarcia.bbdd

import androidx.room.*

@Entity
data class RelacionAsigAlum(
    @Embedded var alumno: Alumno,

    @Relation(
        parentColumn = "codigoAlumno",
        entity = Asignatura::class,
        entityColumn = "codigoAsignatura",
        associateBy = Junction(
            value = AsignaturaAlumnoCrossRef::class,
            parentColumn = "codigoAlumno",
            entityColumn = "codigoAsignatura"
        )
    )

    var asignatura: List<Asignatura>
)

@Entity(primaryKeys = ["codigoAlumno", "codigoAsignatura"])
data class AsignaturaAlumnoCrossRef(
    val codigoAlumno: Int,
    val codigoAsignatura: Int

)


