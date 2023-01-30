package com.example.movcompjelp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador (
    contexto: Context?,):SQLiteOpenHelper( contexto, "moviles", null, 1){

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun crearEntrenador(
        nombre:String,
        descripcion:String
    ):Boolean{
        // this.readableDatabase Lectura
        // this.writableDatabase Escritura
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)

        val resultadoGuardar = basedatosEscritura.insert(
            "ENTRENADOR",    //Tabla
            null,    //
            valoresAGuardar    //valores
        )
        basedatosEscritura.close()
        return if(
            resultadoGuardar.toInt() == -1
        ) false else true

    }

    fun eliminadorEntrenadorFormulario(id:Int): Boolean{
        // this.readableDatabase Lectura
        // this.writableDatabase Escritura
        val conexionEscritura = writableDatabase

        val resultadoEliminacion = conexionEscritura.delete(
            "ENTRENADOR",    //Tabla
            "id=?",    //
            arrayOf(
                id.toString()
            )// valores
        )
        conexionEscritura.close()
        return if(
            resultadoEliminacion.toInt() == -1
        ) false else true
    }

    fun actualizarEntrenadorFormulario(
        nombre:String,
        descripcion:String,
        idActualizar: Int
    ):Boolean{
        // this.readableDatabase Lectura
        // this.writableDatabase Escritura
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)

        val resultadoActualizacion= conexionEscritura.update(
            "ENTRENADOR",    //Tabla
            valoresAActualizar,    // a actualizar
            "id=?",   //Where "condicion"
            arrayOf(
                idActualizar.toString()
            )//valores del where
        )
        conexionEscritura.close()
        return if(
            resultadoActualizacion.toInt() == -1
        ) false else true
    }

    fun consultarEntrenadorPorId(id: Int): BEntrenador{
        //  val baseDatosLectura = this.readableDatabase
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM ENTRENADOR WHERE ID = ?"
        val resultadoConsultarLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            arrayOf(
                id.toString()
            )
        )
        val exiteUsuario = resultadoConsultarLectura.moveToFirst()
        val usuarioEncontrado = BEntrenador(0, "", "")
        //Logica para obtener un usuario
        do{
            val id = resultadoConsultarLectura.getInt(0)
            val nombre = resultadoConsultarLectura.getString(1)
            val descripcion = resultadoConsultarLectura.getString(2)

            if (id != null){
                usuarioEncontrado.id = id
                usuarioEncontrado.nombre = nombre
                usuarioEncontrado.descripcion = descripcion
            }
        } while (resultadoConsultarLectura.moveToNext())
        resultadoConsultarLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }
}