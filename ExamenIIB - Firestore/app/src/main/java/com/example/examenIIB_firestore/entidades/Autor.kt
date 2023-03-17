package com.example.examenIIB_firestore.entidades

data class Autor(
    var idAutor: Int? = null,
    var Nombre: String? = null,
    var Edad: Long? = null,
    var Valoracion: Double? = null,
    var FechaNacimiento: String? = null,
    var Retirado: String? = null
)