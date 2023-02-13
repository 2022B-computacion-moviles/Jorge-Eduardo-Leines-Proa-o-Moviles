package com.example.deber_examen_real.entidades

import java.time.LocalDate

class Libros {
    var idAutorLibros: Int? = null
    var idLibro: Int? = null
    var nombreLibro: String? = null
    var fecha_salida: String? = null
    var puntuacion: Double? = null
    var recomendable: String? = null

    constructor()

    constructor(
        idAutorLibros: Int,
        idLibro: Int,
        nombreLibro: String,
        fecha_salida: String,
        puntuacion: Double,
        recomendable: String
    ) {
        this.idAutorLibros = idAutorLibros
        this.idLibro = idLibro
        this.nombreLibro = nombreLibro
        this.fecha_salida = fecha_salida
        this.puntuacion = puntuacion
        this.recomendable = recomendable
    }
}