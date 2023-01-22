fun main(args: Array<String>) {
    var serv: Servicios = Servicios()
    serv.Iniciar()
    var seguir = 0
    while(seguir === 0){
        println("Ingrese el servicio CRUD que requiera:\n"+
                "A: Crear nuevo autor con libros\n"+
                "B: Leer todo el repositorio\n"+
                "C: Encontrar un Autor y sus Libros por Id\n"+
                "D: Borrar a un Autor\n"+
                "E: Actualizar un Autor por completo\n"+
                "F: Crear Libros de un Autor existente\n"+
                "G: Encontrar un libro de un Autor\n"+
                "H: Actualizar un Libro de un Autor\n"+
                "I: Borrar un Libro de un Autor existente\n"+
                "-->"
        )
        var opcion = readLine()
        when(opcion){
            "A","a" ->{
                serv.crearAutor()
            }
            "B","b" ->{
                serv.obtenerTodosAL()
            }
            "C","c" ->{
                println("Escriba el id del autor que desea buscar:")
                var idAutor: Int = readLine()!!.toInt()
                if(idAutor >= 0 && idAutor < serv.rep.repositorio.size){
                    serv.obtenerALPorId(idAutor)
                }else {
                    println("Ingrese un id existente, puede revisar todos los datos en la opcion 'B' en el menu")
                }
            }
            "D","d" ->{
                println("Escriba el id de un Autor a borrar:")
                var idAutor: Int = readLine()!!.toInt()
                if(idAutor >= 0 && idAutor < serv.rep.repositorio.size){
                    serv.borrarALId(idAutor)
                }else {
                    println("Ingrese un id a borrar existente, puede revisar todos los datos en la opcion 'B' en el menu")
                }
            }
            "E","e" ->{
                println("Escriba el id de un Autor a actualizar:")
                var idAutor: Int = readLine()!!.toInt()
                if(idAutor >= 0 && idAutor < serv.rep.repositorio.size){
                    serv.actualizacionCompleta(idAutor)
                }else {
                    println("Ingrese un id a actualizar existente, puede revisar todos los datos en la opcion 'B' en el menu")
                }
            }
            "F","f" ->{
                println("Escriba el id de un Autor para crear nuevos libros:")
                var idAutor: Int = readLine()!!.toInt()
                if(idAutor >= 0 && idAutor < serv.rep.repositorio.size){
                    serv.pedirCrearLibrosDeUnAutor(idAutor)
                }else {
                    println("Ingrese un id de autor a agregar libros existente, puede revisar todos los datos en la opcion 'B' en el menu")
                }
            }
            "G","g" ->{
                println("Escriba el id de un Autor a buscar:")
                var idAutor: Int = readLine()!!.toInt()
                println("Escriba el id del Libro a buscar dentro del autor con id "+idAutor+":")
                var idLibro: Int = readLine()!!.toInt()
                if((idAutor >= 0 && idAutor < serv.rep.repositorio.size) && (idLibro >= 0 && idLibro < serv.rep.repositorio.get(idAutor).libros.size)){
                    serv.obtenerLibroDeUnAutor(idAutor, idLibro)
                }else {
                    println("Ingrese un id autor y libro existentes, puede revisar todos los datos en la opcion 'B' en le menu")
                }
            }
            "H","h" ->{
                println("Escriba el id de un Autor a actualizar un libro:")
                var idAutor: Int = readLine()!!.toInt()
                println("Escriba el id del Libro a actualizar dentro del autor con id "+idAutor+":")
                var idLibro: Int = readLine()!!.toInt()
                if((idAutor >= 0 && idAutor < serv.rep.repositorio.size) && (idLibro >= 0 && idLibro < serv.rep.repositorio.get(idAutor).libros.size)){
                    serv.actualizarLibroDeAutor(idAutor, idLibro)
                }else {
                    println("Ingrese un id de autor y libro existente para actualizar, puede revisar todos los datos en la opcion 'B' en le menu")
                }
            }
            "I","i" ->{
                println("Escriba el id de un Autor donde desea borrar el libro:")
                var idAutor: Int = readLine()!!.toInt()
                println("Escriba el id del Libro a borrar dentro del autor con id "+idAutor+":")
                var idLibro: Int = readLine()!!.toInt()
                if((idAutor >= 0 && idAutor < serv.rep.repositorio.size) && (idLibro >= 0 && idLibro < serv.rep.repositorio.get(idAutor).libros.size)){
                    serv.borrarLibroDeUnAutor(idAutor,idLibro)
                }else {
                    println("Ingrese un id de Autor y Libro existentes para borrarlos, puede revisar todos los datos en la opcion 'B' en le menu")
                }
            }
            "S", "s"->{
                break
            }
        }
        println("Para volver al menu de opciones ingrese 0, caso contrario 1")
        seguir = readLine()!!.toInt()
    }

}