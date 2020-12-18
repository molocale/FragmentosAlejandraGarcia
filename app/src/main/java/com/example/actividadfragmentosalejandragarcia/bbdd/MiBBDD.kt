package com.example.actividadfragmentosalejandragarcia.bbdd


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Alumno::class, Profesor::class, Asignatura::class, AsignaturaAlumnoCrossRef::class],
    version = 1
)
abstract class MiBBDD : RoomDatabase() {
    abstract fun alumnoDao(): AlumnoDao
    abstract fun profesorDao(): ProfesorDao
    abstract fun asignaturaDao(): AsignaturaDao
    abstract fun relacionAsigAlumDao(): RelacionAsigAlumDao


    companion object {
        private const val DATABASE_NAME = "U-TAD"

        @Volatile
        private var INSTANCE: MiBBDD? = null

        fun getInstance(context: Context): MiBBDD? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MiBBDD::class.java,
                    DATABASE_NAME
                ).build()
            }
            return INSTANCE
        }
    }
}
