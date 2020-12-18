package com.example.actividadfragmentosalejandragarcia.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.actividadfragmentosalejandragarcia.MainActivity
import com.example.actividadfragmentosalejandragarcia.R
import com.example.actividadfragmentosalejandragarcia.bbdd.Alumno
import com.example.actividadfragmentosalejandragarcia.bbdd.DataRepositorio
import com.example.repasorecyclerviewspinner.adapters.AdaptadorRV


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [RecyclerViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecyclerViewFragment : Fragment() {

    var listaAlumnos = ArrayList<Alumno>()
    lateinit var adaptadorRV: AdaptadorRV

    //lateinit var actividad: MainActivity

    // TODO: Rename and change types of parameters
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
        var v = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        var recyclerViewListaAlumnos =
            v.findViewById<View>(R.id.recyclerViewListaAlumnos) as RecyclerView

        var datosRelacion = dataRepositorio.traerRelacionAsigAlum()



        for (eltoAsigAlum in datosRelacion) {
            for (eltoAsig in eltoAsigAlum.asignatura) {
                if (param1 == eltoAsig.nombreAsignatura) {
                    listaAlumnos.add(eltoAsigAlum.alumno)

                }
            }

        }

        //Asi funciona un recyclerView

        recyclerViewListaAlumnos.layoutManager =
            LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        //recyclerView.layoutManager = GridLayoutManager(this, 3)

        //Adaptador Recicler: donde metemos nuestro arraylist (previamente relleno )
        adaptadorRV = AdaptadorRV(listaAlumnos.toList(), context!!)
        recyclerViewListaAlumnos.adapter = adaptadorRV


        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment recyclerView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstanceLista(param1: String) =
            RecyclerViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
}