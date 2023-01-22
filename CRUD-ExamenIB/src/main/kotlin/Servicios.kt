import java.time.LocalDate

class Servicios {
    val rep: Repositorio = Repositorio()
    fun Iniciar(){
        var autor1: Autor = Autor("Eduardo", 22, 56.9, LocalDate.parse("2000-10-10"), true)
        var libro1: Libro = Libro("Rosa", LocalDate.parse("2015-12-11"), 24, 89.8, true)
        var libro2: Libro = Libro("Fake", LocalDate.parse("2016-09-10"), 24, 89.8, true)
        var libros: ArrayList<Libro> = ArrayList()
        libros.add(libro1)
        libros.add(libro2)
        rep.crearRelacion(autor1, libros)
    }

    fun crearAutor(){
        var libros: ArrayList<Libro> = ArrayList()
        var autor: Autor
        var libro: Libro
        var retirado = false
        var recomendado = false
        var seguir = 0
        println("Escriba el nombre del autor: ")
        var nombre_autor = readLine()
        println("Escriba su edad: ")
        var edad: Int = readLine()!!.toInt()
        println("Escriba su valoracion sobre el: ")
        var valo: Double = readLine()!!.toDouble()
        println("Ingrese la fecha de su nacimiento (aaaa-mm-dd): ")
        var fecha = LocalDate.parse(readLine())
        println("Ingrese 0 si esta retirado o 1 si aun no se retira: ")
        var opcion = readLine()!!.toInt()
        if(opcion === 0){
            retirado = true
        }
        else if(opcion === 1){
            retirado = false
        }
        autor = Autor(nombre_autor, edad, valo, fecha, retirado)
        println("La siguiente seccion es para ingresar los libros creados por el autor...")
        while (seguir === 0 ){
            println("Escriba el nombre del libro: ")
            var nombre_libro = readLine()
            println("Escriba su fecha de lanzamiento: ")
            var fecha_salida = LocalDate.parse(readLine())
            println("Escriba su identificador global: ")
            var id: Int = readLine()!!.toInt()
            println("Ingrese su puntutacion sobre el: ")
            var puntu = readLine()!!.toDouble()
            println("Ingrese 0 si es recomendable o 1 si no lo es: ")
            var opcion = readLine()!!.toInt()
            if(opcion === 0 ){
                recomendado = true
            }
            else if(opcion === 1){
                recomendado = false
            }
            libro = Libro(nombre_libro, fecha_salida, id, puntu, recomendado)
            libros.add(libro)
            println("Ingrese 0 si quiere ingresar mas libros del mismo autor o 1 si quiere guardarlo en la base de datos: ")
            seguir = readLine()!!.toInt()
        }
        rep.crearRelacion(autor, libros)
    }

    fun obtenerTodosAL(){
        println("Se esta mostrando todos los libros guardados")
        println(rep.obtenerTodoRepositorio())
    }

    fun obtenerALPorId(idAutor: Int){
        println("El Autor con el id: " + idAutor + "\n")
        println(rep.encontrarPorId(idAutor))
    }

    fun obtenerLibroDeUnAutor(idAutor: Int, idLibro: Int){
        println("El Libro con id " + idLibro + " del autor con id "+idAutor+":\n")
        println(rep.encontrarLibroDeUnAutor(idAutor, idLibro))
    }

    fun pedirCrearLibrosDeUnAutor(idAutor: Int){
        var seguir = 0
        var recomendado = false
        var libro: Libro
        println("La siguiente seccion es para ingresar los libros creados por el autor del id "+idAutor)
        while (seguir === 0 ) {
            println("Escriba el nombre del libro: ")
            var nombre_libro = readLine()
            println("Escriba su fecha de lanzamiento: ")
            var fecha_salida = LocalDate.parse(readLine())
            println("Escriba su identificador global: ")
            var id: Int = readLine()!!.toInt()
            println("Ingrese su puntutacion sobre el: ")
            var puntu = readLine()!!.toDouble()
            println("Ingrese 0 si es recomendable o 1 si no lo es: ")
            var opcion = readLine()!!.toInt()
            if (opcion === 0) {
                recomendado = true
            } else if (opcion === 1) {
                recomendado = false
            }
            libro = Libro(nombre_libro, fecha_salida, id, puntu, recomendado)
            rep.crearLibrosEnAutor(idAutor, libro)
            println("Ingrese 0 si quiere ingresar mas libros del mismo autor o 1 si quiere guardarlo en la base de datos: ")
            seguir = readLine()!!.toInt()
        }
    }

    fun borrarALId(id: Int){
        println("El autor con el id "+ id + " ha sido eliminado")
        rep.borrarPorIdAutor(id)
    }

    fun borrarLibroDeUnAutor(id1: Int, id2: Int){
        println("El libro con el id "+ id2 + " creado por el autor con id "+ id1 + " ha sido eliminado")
        rep.borrarPorIdAutorLibros(id1, id2)
    }

    fun actualizarLibroDeAutor(
        idAutor: Int,
        idLibro: Int){
        var recomendado = false
        var libro: Libro
        println("La siguiente seccion es para ingresar los libros creados por el autor del id "+idAutor)
        println("Escriba el nombre del libro: ")
        var nombre_libro = readLine()
        println("Escriba su fecha de lanzamiento: ")
        var fecha_salida = LocalDate.parse(readLine())
        println("Escriba su identificador global: ")
        var id: Int = readLine()!!.toInt()
        println("Ingrese su puntutacion sobre el: ")
        var puntu = readLine()!!.toDouble()
        println("Ingrese 0 si es recomendable o 1 si no lo es: ")
        var opcion = readLine()!!.toInt()
        if (opcion === 0) {
            recomendado = true
        } else if (opcion === 1) {
            recomendado = false
        }
        libro = Libro(nombre_libro, fecha_salida, id, puntu, recomendado)
        rep.actualizarLibrosAutor(idAutor, idLibro, libro)
    }

    fun actualizacionCompleta(idAutor: Int){
        var libros: ArrayList<Libro> = ArrayList()
        var autor: Autor
        var libro: Libro
        var retirado = false
        var recomendado = false
        var seguir = 0
        println("Escriba el nombre del autor: ")
        var nombre_autor = readLine()
        println("Escriba su edad: ")
        var edad: Int = readLine()!!.toInt()
        println("Escriba su valoracion sobre el: ")
        var valo: Double = readLine()!!.toDouble()
        println("Ingrese la fecha de su nacimiento (aaaa-mm-dd): ")
        var fecha = LocalDate.parse(readLine())
        println("Ingrese 0 si esta retirado o 1 si aun no se retira: ")
        var opcion = readLine()!!.toInt()
        if(opcion === 0){
            retirado = true
        }
        else if(opcion === 1){
            retirado = false
        }
        autor = Autor(nombre_autor, edad, valo, fecha, retirado)
        println("La siguiente seccion es para ingresar los libros creados por el autor...")
        while (seguir === 0){
            println("Escriba el nombre del libro: ")
            var nombre_libro = readLine()
            println("Escriba su fecha de lanzamiento: ")
            var fecha_salida = LocalDate.parse(readLine())
            println("Escriba su identificador global: ")
            var id: Int = readLine()!!.toInt()
            println("Ingrese su puntutacion sobre el: ")
            var puntu = readLine()!!.toDouble()
            println("Ingrese 0 si es recomendable o 1 si no lo es: ")
            var opcion = readLine()!!.toInt()
            if(opcion === 0 ){
                recomendado = true
            }
            else if(opcion === 1){
                recomendado = false
            }
            libro = Libro(nombre_libro, fecha_salida, id, puntu, recomendado)
            libros.add(libro)
            println("Ingrese 0 si quiere ingresar mas libros del mismo autor o 1 si quiere guardarlo en la base de datos: ")
            seguir = readLine()!!.toInt()
        }
        rep.actualizarDatos(idAutor,autor, libros)
    }
}