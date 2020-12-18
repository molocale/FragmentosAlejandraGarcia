package com.example.actividadfragmentosalejandragarcia.bbdd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AsignaturaDao {
    @Query("SELECT * FROM Asignatura")
    fun traerDatosAsignatura(): List<Asignatura>
    @Insert
    fun insertarDatosAsignatura(vararg miAsignatura: Asignatura)
    @Insert
    fun insertarDatosAsignatura( miAsignatura: List<Asignatura>)

}