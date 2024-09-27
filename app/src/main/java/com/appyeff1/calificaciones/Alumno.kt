package com.appyeff1.calificaciones

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alumno(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val nota: Double,
    val grado: String
) : Parcelable
