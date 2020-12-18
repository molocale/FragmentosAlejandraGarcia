package com.example.actividadfragmentosalejandragarcia.bbdd

import android.content.Context
import android.os.AsyncTask


class DataRepositorio(context: Context) {
    private val alumnoDao: AlumnoDao? = MiBBDD.getInstance(context)?.alumnoDao()
    private val profesorDao: ProfesorDao? = MiBBDD.getInstance(context)?.profesorDao()
    private val asignaturaDao: AsignaturaDao? = MiBBDD.getInstance(context)?.asignaturaDao()
    private val relacionAsigAlumDao: RelacionAsigAlumDao? =
        MiBBDD.getInstance(context)?.relacionAsigAlumDao()

    //Query que trae mis alumnos:
    fun traerDatosAlumno(): List<Alumno> {
        return todosMisAlumnos(alumnoDao!!).execute().get()
    }

    private class todosMisAlumnos(private val alumnosDao: AlumnoDao) :
        AsyncTask<Alumno, Void, List<Alumno>>() {
        override fun doInBackground(vararg customers: Alumno?): List<Alumno> {
            return alumnosDao.traerDatosAlumno()
        }
    }

    //Insert de alumnos:
    fun insertAlumno(alumno: Alumno): Int {
        if (alumnoDao != null) {
            return InsertAsyncTask(alumnoDao).execute(alumno).get()
        }
        return -1
    }

    private class InsertAsyncTask(private val alumnoDao: AlumnoDao) :
        AsyncTask<Alumno, Void, Int>() {
        override fun doInBackground(vararg alumnos: Alumno?): Int? {
            try {
                for (alumnos in alumnos) {
                    if (alumnos != null) alumnoDao.insertarDatosAlumno(alumnos)
                }
                return 0
            } catch (exception: Exception) {
                return -1
            }
        }
    }


    //Query que trae mi profesor:
    fun traerDatosProfesor(): List<Profesor> {
        return todosMisProfesores(profesorDao!!).execute().get()
    }

    private class todosMisProfesores(private val profesorDao: ProfesorDao) :
        AsyncTask<Profesor, Void, List<Profesor>>() {
        override fun doInBackground(vararg customers: Profesor?): List<Profesor> {
            return profesorDao.traerDatosProfesor()
        }
    }

    //Insert de profesor:
    fun insertProfesor(profesor: Profesor): Int {
        if (profesorDao != null) {
            return InsertAsyncTask2(profesorDao).execute(profesor).get()
        }
        return -1
    }

    private class InsertAsyncTask2(private val profesorDao: ProfesorDao) :
        AsyncTask<Profesor, Void, Int>() {
        override fun doInBackground(vararg profesores: Profesor?): Int? {
            try {
                for (profesores in profesores) {
                    if (profesores != null) profesorDao.insertarDatosProfesor(profesores)
                }
                return 0
            } catch (exception: Exception) {
                return -1
            }
        }
    }


    //Query que trae mis asignaturas:
    fun traerDatosAsignatura(): List<Asignatura> {
        return todasMisAsignaturas(asignaturaDao!!).execute().get()
    }

    private class todasMisAsignaturas(private val asignaturaDao: AsignaturaDao) :
        AsyncTask<Asignatura, Void, List<Asignatura>>() {
        override fun doInBackground(vararg customers: Asignatura?): List<Asignatura> {
            return asignaturaDao.traerDatosAsignatura()
        }
    }

    //Insert de Asignaturas:
    fun insertAsignatura(asignatura: Asignatura): Int {
        if (asignaturaDao != null) {
            return InsertAsyncTask3(asignaturaDao).execute(asignatura).get()
        }
        return -1
    }

    private class InsertAsyncTask3(private val asignaturaDao: AsignaturaDao) :
        AsyncTask<Asignatura, Void, Int>() {
        override fun doInBackground(vararg asignaturas: Asignatura?): Int? {
            try {
                for (asignaturas in asignaturas) {
                    if (asignaturas != null) asignaturaDao.insertarDatosAsignatura(asignaturas)
                }
                return 0
            } catch (exception: Exception) {
                return -1
            }
        }
    }

    //Query que trae mi relacion alumno-asignatura:
    fun traerRelacionAsigAlum(): List<RelacionAsigAlum> {
        return todasMisRelaciones(relacionAsigAlumDao!!).execute().get()
    }

    private class todasMisRelaciones(private val relacionAsigAlumDao: RelacionAsigAlumDao) :
        AsyncTask<RelacionAsigAlum, Void, List<RelacionAsigAlum>>() {
        override fun doInBackground(vararg customers: RelacionAsigAlum?): List<RelacionAsigAlum> {
            return relacionAsigAlumDao.traerRelacionAsigAlum()
        }
    }
    //Insert de Relacion Asignatura-Alumno:


    fun insertarRelacionAsigAlum(relacionAsigAlum: RelacionAsigAlum): Int {
        if (alumnoDao != null && asignaturaDao != null && relacionAsigAlumDao != null) {
            return InsertAsyncTaskAsigAlum(alumnoDao, asignaturaDao, relacionAsigAlumDao).execute(
                relacionAsigAlum
            ).get()
        }
        return -1
    }


    private class InsertAsyncTaskAsigAlum(
        private val alumnoDao: AlumnoDao,
        private val asignaturaDao: AsignaturaDao,
        private val relacionAsigAlumDao: RelacionAsigAlumDao
    ) : AsyncTask<RelacionAsigAlum, Void, Int>() {
        override fun doInBackground(vararg asignaturasEnAlumnos: RelacionAsigAlum?): Int? {
            try {

                for (asignaturasEnAlumnos in asignaturasEnAlumnos) {
                    if (asignaturasEnAlumnos != null) {
//                        alumnoDao!!.insertarDatosAlumno(asignaturasEnAlumnos.alumno)
//                        asignaturaDao!!.insertarDatosAsignatura(asignaturasEnAlumnos.asignatura)

                        for (asignatura in asignaturasEnAlumnos.asignatura) {
                            relacionAsigAlumDao.insertarRelacionAsigAlum(
                                AsignaturaAlumnoCrossRef(
                                    asignaturasEnAlumnos.alumno.codigoAlumno,
                                    asignatura.codigoAsignatura
                                )
                            )
                        }
                    }
                }
                return 0
            } catch (exception: Exception) {
                return -1

            }


        }
    }
}