package com.example.actividadfragmentosalejandragarcia.bbdd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Profesor (
    @PrimaryKey val codigoProfesor: Int?,
    @ColumnInfo(name = "nombreProfesor") val nombreProfesor : String?,
    @ColumnInfo(name = "apellidoProfesor") val apellidoProfesor : String?,
    @ColumnInfo(name = "asignaturaProfesor") val asignaturaProfesor : String?
)