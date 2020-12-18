package com.example.actividadfragmentosalejandragarcia.bbdd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alumno (
    @PrimaryKey val codigoAlumno: Int,
    @ColumnInfo (name = "nombreAlumno") val nombreAlumno : String?,
    @ColumnInfo (name = "apellidoAlumno") val apellidoAlumno : String?
    )