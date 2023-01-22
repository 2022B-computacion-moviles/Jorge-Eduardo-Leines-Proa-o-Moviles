import java.time.LocalDate

class Autor {
    var nombre: String? = null
    var edad: Int? = null
    var valoracion: Double? = null
    var fechanacimiento: LocalDate? = null
    var retirado: Boolean? = null

    constructor(nombre: String?, edad: Int?, valoracion: Double?, fechanacimiento: LocalDate?, retirado: Boolean?) {
        this.nombre = nombre
        this.edad = edad
        this.valoracion = valoracion
        this.fechanacimiento = fechanacimiento
        this.retirado = retirado
    }

    constructor()

    override fun toString(): String {
        return "$nombre, edad=$edad, valoracion=$valoracion, fechanacimiento=$fechanacimiento, retirado=$retirado"
    }
}