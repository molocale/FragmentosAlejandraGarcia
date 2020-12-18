package com.example.actividadfragmentosalejandragarcia.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.actividadfragmentosalejandragarcia.R
import com.example.actividadfragmentosalejandragarcia.bbdd.Alumno
import com.example.actividadfragmentosalejandragarcia.bbdd.DataRepositorio
import com.example.actividadfragmentosalejandragarcia.bbdd.RelacionAsigAlum



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DatosAlumnoFragment : Fragment() {


    private var param1: Int? = null

    private var param2: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getStringArrayList(ARG_PARAM2)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val v = inflater.inflate(R.layout.fragment_datos_alumno, container, false)

        var textViewNombreAlumno = v.findViewById<View>(R.id.textViewNombreAlumno) as TextView
        var textViewApellidoAlumno = v.findViewById<View>(R.id.textViewApellidoAlumno) as TextView
        var textViewAsignaturas = v.findViewById<View>(R.id.textViewAsignaturas) as TextView


        if ( param1 != -1){
            textViewNombreAlumno.text = param2!!.get(0)
            textViewApellidoAlumno.text = param2!!.get(1)
            var misAsignaturasFrag = ""
            for (i in 2 until param2!!.size){
                misAsignaturasFrag += param2!!.get(i) + " "

            }
            textViewAsignaturas.text = misAsignaturasFrag
        }

        return v
    }

    companion object {
        @JvmStatic
        fun newInstanceAlum(param1: Int, param2: ArrayList<String>) =
            DatosAlumnoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putStringArrayList(ARG_PARAM2, param2)

                }
            }
    }
}