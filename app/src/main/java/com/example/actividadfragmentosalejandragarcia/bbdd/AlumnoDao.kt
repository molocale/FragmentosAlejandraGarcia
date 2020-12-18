package com.example.actividadfragmentosalejandragarcia.bbdd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlumnoDao {
    @Query ("SELECT * FROM Alumno")
    fun traerDatosAlumno(): List<Alumno>

    @Insert
    fun insertarDatosAlumno(vararg miAlumno : Alumno)

}