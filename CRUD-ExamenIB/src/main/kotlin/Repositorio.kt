import java.io.File

class Repositorio {
    var repositorio: MutableList<AutorYLibros> = mutableListOf()
    var consola = "consola.txt"
    var rep = "repositorio.txt"

    fun crearRelacion(
        autor: Autor,
        libros: ArrayList<Libro>
    ){
        var lib: String
        repositorio!!.add(AutorYLibros(autor,libros))
        File(consola).writeText("")
        File(rep).writeText("")
        for(i in 0 until repositorio.size){
            File(consola).appendText("Id: " + i.toString() + "\t" +
                    "Autor: " + repositorio.get(i).autor +
                    "\n\t\tLibros: " + "`id:0(...),1(...),...`-> "+repositorio.get(i).libros+"\n")
            File(rep).appendText(i.toString()+ "\t" + repositorio.get(i).toString() + "\n")
        }
    }

    fun crearLibrosEnAutor(
        idAutor: Int,
        libro: Libro
    ) {
        repositorio.get(idAutor).libros.add(libro)
        File(consola).writeText("")
        File(rep).writeText("")
        for(i in 0 until repositorio.size){
            File(consola).appendText("Id: " + i.toString() + "\t" +
                    "Autor: " + repositorio.get(i).autor +
                    "\n\t\tLibros: " + "`id:0(...),1(...),...`-> "+repositorio.get(i).libros+"\n")
            File(rep).appendText(i.toString()+ "\t" + repositorio.get(i).toString() + "\n")
        }
    }

    fun obtenerTodoRepositorio (): String {
        return File(consola).readText()
    }

    fun encontrarPorId(id: Int): String? {
        return id.toString() + "\t" +
                "Autor: " + repositorio.get(id).autor +
                "\n\tLibros: "+ repositorio.get(id).libros+"\n"
    }

    fun encontrarLibroDeUnAutor(idAutor: Int, idLibro: Int): String? {
        return "ID:" + idAutor.toString() + "\t" +
                "Autor creador: " + repositorio.get(idAutor).autor +
                "\n\t\tLibro escrito: "+ repositorio.get(idAutor).libros.get(idLibro)+"\n"
    }

    fun borrarPorIdAutor(id: Int): String?{
        repositorio.removeAt(id)
        File(consola).writeText("")
        File(rep).writeText("")
        for(i in 0 until repositorio.size){
            File(consola).appendText("Id: " + i.toString() + "\t" +
                    "Autor: " + repositorio.get(i).autor +
                    "\n\t\tLibros: " + "`id:0(...),1(...),...`-> "+repositorio.get(i).libros+"\n")
            File(rep).appendText(i.toString()+ "\t" + repositorio.get(i).toString() + "\n")
        }
        return "Se ha eliminado y actualizado la base de datos: \n"+ File("consola.txt").readText()
    }

    fun borrarPorIdAutorLibros(idAutor: Int, idLibro: Int): String? {
        repositorio.get(idAutor).libros.removeAt(idLibro)
        File(consola).writeText("")
        File(rep).writeText("")
        for(i in 0 until repositorio.size){
            File(consola).appendText("Id: " + i.toString() + "\t" +
                    "Autor: " + repositorio.get(i).autor +
                    "\n\t\tLibros: " + "`id:0(...),1(...),...`-> "+repositorio.get(i).libros+"\n")
            File(rep).appendText(i.toString()+ "\t" + repositorio.get(i).toString() + "\n")
        }
        return "Se ha eliminado y actualizado la base de datos: \n"+ File("consola.txt").readText()
    }

    fun actualizarLibrosAutor(idAutor: Int, idLibro: Int,libro: Libro): String {
        repositorio.get(idAutor).libros.set(idLibro, libro)
        File(consola).writeText("")
        File(rep).writeText("")
        for(i in 0 until repositorio.size){
            File(consola).appendText("Id: " + i.toString() + "\t" +
                    "Autor: " + repositorio.get(i).autor +
                    "\n\t\tLibros: " + "`id:0(...),1(...),...`-> "+repositorio.get(i).libros+"\n")
            File(rep).appendText(i.toString()+ "\t" + repositorio.get(i).toString() + "\n")
        }
        return "Se ha actualizado la base de datos: \n"+ File("consola.txt").readText()
    }

    fun actualizarDatos(
        id: Int,
        autor: Autor,
        libros: ArrayList<Libro>): String? {
        repositorio.set(id, AutorYLibros(autor,libros))
        File(consola).writeText("")
        File(rep).writeText("")
        for(i in 0 until repositorio.size){
            File(consola).appendText("Id: " + i.toString() + "\t" +
                    "Autor: " + repositorio.get(i).autor +
                    "\n\t\tLibros: " + "`id:0(...),1(...),...`-> "+repositorio.get(i).libros+"\n")
            File(rep).appendText(i.toString()+ "\t" + repositorio.get(i).toString() + "\n")
        }
        return "Se actualizo el dato de id: "+ id + "\n"+ File("consola.txt").readText()
    }
}