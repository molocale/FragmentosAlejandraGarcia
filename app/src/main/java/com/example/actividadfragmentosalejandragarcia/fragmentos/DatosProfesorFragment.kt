package com.example.actividadfragmentosalejandragarcia.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.actividadfragmentosalejandragarcia.R
import com.example.actividadfragmentosalejandragarcia.bbdd.DataRepositorio

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var dataRepositorio: DataRepositorio


class DatosProfesorFragment : Fragment() {

    lateinit var textViewNombreProfesor: TextView
    lateinit var textViewApellidoProfesor: TextView
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_datos_profesor, container, false)

        textViewNombreProfesor = v.findViewById(R.id.textViewNombreProfesor)
        textViewApellidoProfesor = v.findViewById(R.id.textViewApellidoProfesor)

        //Inicializamos el repositorio
        dataRepositorio = DataRepositorio(context!!)


        var datosProfesorData = dataRepositorio.traerDatosProfesor()
        for (eltoProfe in datosProfesorData) {
            if (param1 == eltoProfe.asignaturaProfesor) {
                textViewNombreProfesor.text = eltoProfe.nombreProfesor
                textViewApellidoProfesor.text = eltoProfe.apellidoProfesor
            }
        }
        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            DatosProfesorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}