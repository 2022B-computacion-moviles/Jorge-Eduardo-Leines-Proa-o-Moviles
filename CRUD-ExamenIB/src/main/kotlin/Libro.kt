import java.time.LocalDate

class Libro {
    var nombre : String? = null
    var fecha_salida : LocalDate? = null
    var id : Int? = null
    var puntuacion: Double? = null
    var recomendable: Boolean? = null

    constructor(nombre: String?, fecha_salida: LocalDate?, id: Int?, puntuacion: Double?, recomendable: Boolean?) {
        this.nombre = nombre
        this.fecha_salida = fecha_salida
        this.id = id
        this.puntuacion = puntuacion
        this.recomendable = recomendable
    }

    constructor()


    override fun toString(): String {
        return "(nombre=$nombre, fechalanzamiento=$fecha_salida, id=$id, puntuacion=$puntuacion, recomendable=$recomendable)"
    }


}