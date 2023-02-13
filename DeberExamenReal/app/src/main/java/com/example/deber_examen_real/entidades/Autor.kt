package com.example.deber_examen_real.entidades

import java.time.LocalDate

class Autor {
    var idAutor: Int? = null
    var nombre: String? = null
    var edad: Int? = null
    var valoracion: Double? = null
    var fechanacimiento: String? = null
    var retirado: String? = null

    constructor()

    constructor(
        idAutor: Int,
        nombre: String,
        edad: Int,
        valoracion: Double,
        fechanacimiento: String,
        retirado: String
    ) {
        this.idAutor = idAutor
        this.nombre = nombre
        this.edad = edad
        this.valoracion = valoracion
        this.fechanacimiento = fechanacimiento
        this.retirado = retirado
    }


}