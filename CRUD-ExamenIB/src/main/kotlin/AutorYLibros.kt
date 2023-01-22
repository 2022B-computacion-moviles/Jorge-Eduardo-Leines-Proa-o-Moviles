import java.time.LocalDate

class AutorYLibros {
    var autor: Autor = Autor()
    var libros: ArrayList<Libro> = ArrayList()

    constructor(autor: Autor, libros: ArrayList<Libro>) {
        this.autor = autor
        this.libros = libros
    }

    override fun toString(): String {
        return "$autor, $libros"
    }


}