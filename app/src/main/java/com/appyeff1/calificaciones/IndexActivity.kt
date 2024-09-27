package com.appyeff1.calificaciones

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appyeff1.calificaciones.R


class IndexActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerViewAlumnos: RecyclerView
    private lateinit var alumnosAdapter: AlumnoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)

        databaseHelper = DatabaseHelper(this)

        // Inicializa el RecyclerView
        recyclerViewAlumnos = findViewById(R.id.recyclerViewAlumnos)
        recyclerViewAlumnos.layoutManager = LinearLayoutManager(this)

        // Obtiene todos los alumnos y configura el adaptador
        updateRecyclerView()

        // Configura el botón para agregar un nuevo alumno
        val buttonAgregar: Button = findViewById(R.id.buttonAgregar)
        buttonAgregar.setOnClickListener {
            val intent = Intent(this, AddAlumnoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateRecyclerView() {
        val alumnos = databaseHelper.getAllAlumnos()
        alumnosAdapter = AlumnoAdapter(alumnos) { alumno ->
            showEditDeleteDialog(alumno) // Muestra el diálogo para editar o eliminar
        }
        recyclerViewAlumnos.adapter = alumnosAdapter
    }

    private fun showEditDeleteDialog(alumno: Alumno) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Seleccionar acción")
        dialogBuilder.setMessage("¿Qué deseas hacer con el alumno ${alumno.nombre}?")

        dialogBuilder.setPositiveButton("Editar") { dialog, _ ->
            // Implementa la lógica para editar el alumno
            dialog.dismiss()
        }

        dialogBuilder.setNegativeButton("Eliminar") { dialog, _ ->
            // Lógica para eliminar el alumno
            databaseHelper.deleteAlumno(alumno.id)
            updateRecyclerView() // Actualiza la lista después de eliminar
            dialog.dismiss()
        }

        dialogBuilder.setNeutralButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    override fun onResume() {
        super.onResume()
        updateRecyclerView() // Actualiza la lista al volver a la actividad
    }
}
