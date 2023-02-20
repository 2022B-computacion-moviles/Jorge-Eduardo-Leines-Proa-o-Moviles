package com.example.deber_02_recyclerview.clases

class Mensajes {
    var Nombre: String
    var ImagenId: Int
    var mensajeText: String
    var mensajeImagen: Int

    constructor(Nombre: String, ImagenId: Int, mensajeText: String, mensajeImagen: Int) {
        this.Nombre = Nombre
        this.ImagenId = ImagenId
        this.mensajeText = mensajeText
        this.mensajeImagen = mensajeImagen
    }
}