package com.example.actividadfragmentosalejandragarcia.bbdd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Asignatura (
    @PrimaryKey val codigoAsignatura: Int,
    @ColumnInfo(name = "nombreAsignatura") val nombreAsignatura : String?

)