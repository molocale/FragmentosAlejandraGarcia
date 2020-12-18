package com.example.repasorecyclerviewspinner.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.actividadfragmentosalejandragarcia.MainActivity
import com.example.actividadfragmentosalejandragarcia.R
import com.example.actividadfragmentosalejandragarcia.bbdd.Alumno



class AdaptadorRV(var listaAlumnos: List<Alumno>, private val context: Context) :
    RecyclerView.Adapter<AdaptadorRV.ViewHolder>() {

    lateinit var mainActivity : MainActivity




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Creamos el diseño del alumno
        val miInflador_layaut =
            LayoutInflater.from(parent.context).inflate(R.layout.disenio_alumno, parent, false)
        val viewHolder = ViewHolder(miInflador_layaut)
         mainActivity = context as MainActivity
        //viewHolder.actividad = actividad
        return viewHolder

    }

    class ViewHolder(miinfladorLayaut: View): RecyclerView.ViewHolder(miinfladorLayaut) {
        lateinit var actividad: MainActivity
        var nombreAlumnoLista = miinfladorLayaut.findViewById<TextView>(R.id.textViewNombreAlumnoLista)
        fun enlazar(miAlumno: Alumno) {
            nombreAlumnoLista.text = miAlumno.nombreAlumno

        }
    }


    override fun getItemCount(): Int {
        return listaAlumnos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.enlazar(listaAlumnos[position])
        holder.itemView.setOnClickListener{ mainActivity.onClickItem(listaAlumnos[position]) }


    }




}


