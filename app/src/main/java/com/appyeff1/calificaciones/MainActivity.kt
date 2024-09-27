package com.appyeff1.calificaciones

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var alumnosAdapter: AlumnoAdapter
    private lateinit var editTextName: EditText
    private lateinit var editTextSurname: EditText
    private lateinit var editTextGrade: EditText
    private lateinit var editTextLevel: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicializar componentes
        editTextName = findViewById(R.id.editTextName)
        editTextSurname = findViewById(R.id.editTextSurname)
        editTextGrade = findViewById(R.id.editTextGrade)
        editTextLevel = findViewById(R.id.editTextLevel)
        recyclerView = findViewById(R.id.recyclerView)

        databaseHelper = DatabaseHelper(this)

        // Configurar RecyclerView con el adaptador y manejar clics
        recyclerView.layoutManager = LinearLayoutManager(this)
        alumnosAdapter = AlumnoAdapter(databaseHelper.getAllAlumnos()) { alumno ->
            // Lógica cuando se hace clic en un alumno
            showEditDeleteDialog(alumno)
        }
        recyclerView.adapter = alumnosAdapter

        // Configurar botón para agregar alumno
        val buttonAdd: Button = findViewById(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            addAlumno()
        }

        // Configurar botón para ir a IndexActivity
        val buttonGoToIndex: Button = findViewById(R.id.buttonGoToIndex)
        buttonGoToIndex.setOnClickListener {
            val intent = Intent(this, IndexActivity::class.java)
            startActivity(intent)
        }

        // Actualiza la lista al iniciar la actividad
        updateRecyclerView()
    }

    // Método para agregar un alumno
    private fun addAlumno() {
        val nombre = editTextName.text.toString()
        val apellido = editTextSurname.text.toString()
        val nota = editTextGrade.text.toString().toDoubleOrNull() ?: 0.0
        val grado = editTextLevel.text.toString()

        if (nombre.isNotBlank() && apellido.isNotBlank() && grado.isNotBlank()) {
            databaseHelper.addAlumno(nombre, apellido, nota, grado)
            clearFields()
            updateRecyclerView()
        }
    }

    // Método para limpiar los campos
    private fun clearFields() {
        editTextName.text.clear()
        editTextSurname.text.clear()
        editTextGrade.text.clear()
        editTextLevel.text.clear()
    }

    // Método para actualizar la lista de alumnos en el RecyclerView
    private fun updateRecyclerView() {
        alumnosAdapter.updateData(databaseHelper.getAllAlumnos())
    }

    // Método para mostrar el diálogo de editar o eliminar alumno
    private fun showEditDeleteDialog(alumno: Alumno) {
        // Aquí puedes implementar la lógica para editar o eliminar el alumno
    }
}
