package com.example.actividadfragmentosalejandragarcia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.actividadfragmentosalejandragarcia.bbdd.*
import com.example.actividadfragmentosalejandragarcia.fragmentos.DatosAlumnoFragment
import com.example.actividadfragmentosalejandragarcia.fragmentos.DatosAlumnoFragment.Companion.newInstanceAlum
import com.example.actividadfragmentosalejandragarcia.fragmentos.DatosProfesorFragment
import com.example.actividadfragmentosalejandragarcia.fragmentos.DatosProfesorFragment.Companion.newInstance
import com.example.actividadfragmentosalejandragarcia.fragmentos.RecyclerViewFragment
import com.example.actividadfragmentosalejandragarcia.fragmentos.RecyclerViewFragment.Companion.newInstanceLista
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.security.AccessController.getContext


class MainActivity : AppCompatActivity() {

    //Fragment

    var frameLayoutDatosAlumno: FrameLayout? = null
    var frameLayoutListaAlumno: FrameLayout? = null
    var frameLayoutDatosProfesor: FrameLayout? = null

    var datosAlumnoFragment: DatosAlumnoFragment? = null
    var datosProfesorFragment: DatosProfesorFragment? = null
    var recyclerViewFragment: RecyclerViewFragment? = null


    lateinit var dataRepositorio: DataRepositorio

    lateinit var spinnerAsignaturas: Spinner

    var identificador = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataRepositorio = DataRepositorio(this)

        onReadJson()

        frameLayoutDatosProfesor = findViewById(R.id.frameLayoutDatosProfesor)
        frameLayoutListaAlumno = findViewById(R.id.frameLayoutListaAlumnos)
        frameLayoutDatosAlumno = findViewById(R.id.frameLayoutDatosAlumno)


        //FUNCIONAMIENTO DEL SPINNER

        var misAsignBBDD = dataRepositorio.traerDatosAsignatura()
        var misAsignaturasSpinner = ArrayList<String>()

        for (asignaturaElto in misAsignBBDD) {
            asignaturaElto.nombreAsignatura?.let { misAsignaturasSpinner.add(it) }
        }

        spinnerAsignaturas = findViewById<Spinner>(R.id.spinnerAsignaturas)
        var adaptadorSpinner =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                misAsignaturasSpinner.toArray()
            )
        spinnerAsignaturas.adapter = adaptadorSpinner


        //cada vez que se elija algo en el Spinner

        spinnerAsignaturas.onItemSelectedListener = (object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                var miAsignaturaSeleccionada = parent!!.getItemAtPosition(position)

                reloadBack(miAsignaturaSeleccionada.toString())

            }


            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })

    }

    fun onClickItem(alumno: Alumno) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        var alumnoEnString = ArrayList<String>()
        alumnoEnString.add(alumno.nombreAlumno!!)
        alumnoEnString.add(alumno.apellidoAlumno!!)

        var misAsigAlum = dataRepositorio.traerRelacionAsigAlum()

        for (elto in misAsigAlum) {
            if (alumno.codigoAlumno == elto.alumno.codigoAlumno) {
                for (x in elto.asignatura) {
                    alumnoEnString.add(x.nombreAsignatura!!)
                }
            }
        }
        datosAlumnoFragment = newInstanceAlum(alumno.codigoAlumno, alumnoEnString)

        if (frameLayoutDatosAlumno != null) {
            fragmentTransaction.replace(R.id.frameLayoutDatosAlumno, datosAlumnoFragment!!)

        } else {

            spinnerAsignaturas.visibility = View.GONE
            fragmentTransaction.replace(R.id.frameLayoutDatosProfesor, datosAlumnoFragment!!)
            fragmentTransaction.remove(recyclerViewFragment!!)
            identificador = 1
        }
        fragmentTransaction.commit()



    }

    fun onReadJson() {
        //var imprimir = findViewById<TextView>(R.id.textView4)
        var bufferedReaderRecurso =
            BufferedReader(InputStreamReader(resources.openRawResource(R.raw.datos)))
        var textoLeido = bufferedReaderRecurso.readLine()
        var json = ""
        while (textoLeido != null) {
            json += textoLeido
            textoLeido = bufferedReaderRecurso.readLine();
        }

        var elementos = ""
        val jsonObjeto = JSONObject(json)

        val misProfesores = jsonObjeto.getJSONArray("profesores")
        val misAlumnos = jsonObjeto.getJSONArray("alumnos")
        val misAsignaturas = jsonObjeto.getJSONArray("asignaturas")


        var arrayListProfesor = ArrayList<Profesor>()
        var arrayListAsignatura = ArrayList<Asignatura>()
        var arrayListAlumnos = ArrayList<Alumno>()
        var arrayListRelacion = ArrayList<RelacionAsigAlum>()

        //Profesores

        for (i in 0 until misProfesores.length()) {
            val item = misProfesores.getJSONObject(i)
            val codigoProfesor = item.optString("codigo")
            val nombreProfesor = item.optString("nombre")
            val apellidoProfesor = item.optString("apellido")
            val asignaturaProfesor = item.optString("asignatura")

            var miProfesor = Profesor(
                codigoProfesor.toInt(),
                nombreProfesor,
                apellidoProfesor,
                asignaturaProfesor
            )

            arrayListProfesor.add(miProfesor)

        }


        //Asignaturas

        for (i in 0 until misAsignaturas.length()) {
            val asignaturaElto = misAsignaturas.get(i)


            var miAsignatura = Asignatura(i, asignaturaElto.toString())
            arrayListAsignatura.add(miAsignatura)

        }
        //imprimir.text = arrayListAsignatura.toString()

        //Alumnos

        for (i in 0 until misAlumnos.length()) {
            val item = misAlumnos.getJSONObject(i)
            val asignaturasAlumnos: JSONArray = item.getJSONArray("Asignaturas")
            val codigoAlumno = item.optString("codigo")
            val nombreAlumno = item.optString("nombre")
            val apellidoAlumno = item.optString("apellido")

            var arrayListAsig = ArrayList<Asignatura>()

            for (e in 0 until asignaturasAlumnos.length()) {
                var miCodigo: Int = 0
                for (x in 0 until arrayListAsignatura.size) {


                    if (arrayListAsignatura.get(x).nombreAsignatura == asignaturasAlumnos.get(e)
                            .toString()
                    ) {
                        miCodigo = arrayListAsignatura.get(x).codigoAsignatura

                    }
                }
                var asignatura = Asignatura(miCodigo, asignaturasAlumnos.get(e).toString())
                arrayListAsig.add(asignatura)
                //imprimir.text = item2.toString()

            }


            var miAlumno = Alumno(codigoAlumno.toInt(), nombreAlumno, apellidoAlumno)
            var miRelacion = RelacionAsigAlum(miAlumno, arrayListAsig)
            arrayListAlumnos.add(miAlumno)
            arrayListRelacion.add(miRelacion)

        }


        bufferedReaderRecurso.close()


        insertarAsignaturas(arrayListAsignatura)
        insertarAlumnos(arrayListAlumnos)
        insertarProfesores(arrayListProfesor)
        insertarRelacionAsigAlum(arrayListRelacion)


    }

    private fun insertarAsignaturas(arrayListAsignatura: ArrayList<Asignatura>) {

        for (e in 0 until arrayListAsignatura.size) {
            dataRepositorio.insertAsignatura(arrayListAsignatura.get(e))
        }

    }

    private fun insertarRelacionAsigAlum(arrayListRelacion: ArrayList<RelacionAsigAlum>) {
        for (e in 0 until arrayListRelacion.size) {
            dataRepositorio.insertarRelacionAsigAlum(arrayListRelacion.get(e))
        }
    }


    private fun insertarProfesores(arrayListProfesor: ArrayList<Profesor>) {
        for (e in 0 until arrayListProfesor.size) {
            dataRepositorio.insertProfesor(arrayListProfesor.get(e))
        }
    }


    private fun insertarAlumnos(arrayListAlumnos: ArrayList<Alumno>) {
        for (e in 0 until arrayListAlumnos.size) {
            dataRepositorio.insertAlumno(arrayListAlumnos.get(e))
        }
    }


    override fun onBackPressed() {

        if (identificador == 1 ) {
            spinnerAsignaturas.visibility = View.VISIBLE

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(
                R.id.frameLayoutDatosProfesor,
                newInstance(spinnerAsignaturas.selectedItem.toString())
            )

            fragmentTransaction.replace(
                R.id.frameLayoutListaAlumnos,
                newInstanceLista(spinnerAsignaturas.selectedItem.toString())
            )

            fragmentTransaction.commit()
            identificador = 0

            reloadBack(spinnerAsignaturas.selectedItem.toString())


        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("SALIR DE LA APP_FRAGMENTOS_ALEJANDRA")
            builder.setMessage("¿Está seguro que quiere salir?")

            builder.setPositiveButton("Si") { dialog, _ -> finish() }
            builder.setNegativeButton("No") { dialog, which -> }
            builder.show()
        }





    }
    fun reloadBack (miAsignaturaSeleccionada: String){

        datosProfesorFragment = newInstance(miAsignaturaSeleccionada.toString())
        recyclerViewFragment = newInstanceLista(miAsignaturaSeleccionada.toString())
        datosAlumnoFragment = newInstanceAlum(-1, ArrayList<String>())

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (frameLayoutDatosAlumno == null) {
            // VERTICAL-->DATOS ALUM: NULO
            fragmentTransaction.replace(
                R.id.frameLayoutDatosProfesor,
                datosProfesorFragment!!
            )
            fragmentTransaction.replace(
                R.id.frameLayoutListaAlumnos,
                recyclerViewFragment!!
            )
        } else {
            //HORIZONTAL
            fragmentTransaction.replace(
                R.id.frameLayoutDatosProfesor,
                datosProfesorFragment!!
            )
            fragmentTransaction.replace(
                R.id.frameLayoutListaAlumnos,
                recyclerViewFragment!!
            )
            fragmentTransaction.replace(R.id.frameLayoutDatosAlumno, datosAlumnoFragment!!)
        }
        fragmentTransaction.commit()
    }
}