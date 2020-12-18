package com.example.actividadfragmentosalejandragarcia.bbdd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProfesorDao {
    @Query("SELECT * FROM Profesor")
    fun traerDatosProfesor(): List<Profesor>

    @Insert
    fun insertarDatosProfesor(vararg miProfesor : Profesor)
}