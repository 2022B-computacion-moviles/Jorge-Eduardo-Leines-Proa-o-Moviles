package com.example.deber_examen_real.basedatos

class SQL {
    companion object {
        //constantes campos tabla autor
        const val TABLA_AUTOR: String = "autor"
        const val COL_IDAUTOR: String = "idAutor"
        const val COL_NOMBRE: String = "nombre"
        const val COL_EDAD: String = "edad"
        const val COL_VALORACION: String = "valoracion"
        const val COL_FECHA_NACIMIENTO: String = "fechanacimiento"
        const val COL_RETIRADO: String = "retirado"

        const val CREAR_TABLA_AUTOR="CREATE TABLE " +
                ""+ TABLA_AUTOR+" ("+ COL_IDAUTOR+" "+"INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_NOMBRE+ " TEXT,"+
                COL_EDAD+ " TEXT,"+
                COL_VALORACION+ " TEXT,"+
                COL_FECHA_NACIMIENTO+ " TEXT,"+
                COL_RETIRADO+ " TEXT)"
        //Constantes campos tabla libros
        const val TABLA_LIBROS: String = "libros"
        const val COL_ID_LIBRO: String = "idLibro"
        const val COL_NOMBRE_LIBRO: String = "nombreLibro"
        const val COL_FECHA_SALIDA: String = "fecha_salida"
        const val COL_PUNTUACION: String = "puntuacion"
        const val COL_RECOMENDABLE: String = "recomendable"
        const val COL_ID_AUTOR_LIBRO: String = "idAutorLibros"

        const val CREAR_TABLA_LIBRO:String = "CREATE TABLE "+
                ""+ TABLA_LIBROS+" ("+ COL_ID_LIBRO+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_NOMBRE_LIBRO+" TEXT,"+ COL_FECHA_SALIDA+" TEXT,"+
                COL_PUNTUACION+" TEXT,"+ COL_RECOMENDABLE+" TEXT,"+
                COL_ID_AUTOR_LIBRO+" INTEGER, " +
                "CONSTRAINT fk_id_autor FOREIGN KEY (${COL_ID_AUTOR_LIBRO}) REFERENCES ${TABLA_AUTOR}(${COL_IDAUTOR}) ON DELETE CASCADE)"

        //CONSTRAINT fk_departments
        //FOREIGN KEY (department_id)
        //REFERENCES departments(department_id)
        //ON DELETE CASCADE

    }
}