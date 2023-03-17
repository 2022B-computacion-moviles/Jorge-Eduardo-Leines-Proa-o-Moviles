package com.example.examenIIB_firestore.entidades

data class Libros (
    var idAutor: Int? = null,
    var idLibro: Int? = null,
    var NombreLibro: String? = null,
    var Fecha_salida: String? = null,
    var Puntuacion: Double? = null,
    var Recomendable: String? = null
)