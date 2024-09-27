package com.appyeff1.calificaciones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlumnoAdapter(
    private var alumnos: List<Alumno>,
    private val onItemClick: (Alumno) -> Unit
) : RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder>() {

    class AlumnoViewHolder(itemView: View, private val onItemClick: (Alumno) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        private val textViewApellido: TextView = itemView.findViewById(R.id.textViewApellido)
        private val textViewNota: TextView = itemView.findViewById(R.id.textViewNota)
        private val textViewGrado: TextView = itemView.findViewById(R.id.textViewGrado)

        fun bind(alumno: Alumno) {
            textViewNombre.text = alumno.nombre
            textViewApellido.text = alumno.apellido
            textViewNota.text = "Nota: ${alumno.nota}"
            textViewGrado.text = "Grado: ${alumno.grado}"

            itemView.setOnClickListener { onItemClick(alumno) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alumno, parent, false)
        return AlumnoViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: AlumnoViewHolder, position: Int) {
        holder.bind(alumnos[position])
    }

    override fun getItemCount(): Int {
        return alumnos.size
    }

    fun updateData(newAlumnos: List<Alumno>) {
        alumnos = newAlumnos
        notifyDataSetChanged()
    }
}

