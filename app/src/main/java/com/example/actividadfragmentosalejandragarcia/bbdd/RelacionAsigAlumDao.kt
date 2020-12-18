package com.example.actividadfragmentosalejandragarcia.bbdd

import androidx.room.*

@Dao
interface RelacionAsigAlumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertarRelacionAsigAlum(join: AsignaturaAlumnoCrossRef)

    @Transaction
    @Query("SELECT * FROM Alumno")
    fun traerRelacionAsigAlum(): List<RelacionAsigAlum>
}
