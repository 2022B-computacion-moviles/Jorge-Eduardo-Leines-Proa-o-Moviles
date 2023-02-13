package com.example.deber_examen_real


import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.deber_examen_real.basedatos.ConexionSQLiteHelper
import com.example.deber_examen_real.basedatos.SQL
import com.example.deber_examen_real.entidades.Autor
import com.example.deber_examen_real.entidades.Libros

class librosActividad : AppCompatActivity() {
    var listViewLibros: ListView? = null
    var listaInfoLibros: ArrayList<String>? = null
    var listaLibros: ArrayList<Libros>? = null
    var conn: ConexionSQLiteHelper? = null
    var idItemSeleccionado = 0
    override fun onRestart() {
        super.onRestart()
        recreate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libros_actividad)
        var info = intent.getStringExtra("infoId")
        val botonCrearLibro = findViewById<Button>(R.id.btn_crear_libros_main)
        botonCrearLibro.setOnClickListener{
            irActividad(crearLibro::class.java)
        }
        val txtViewAutor = findViewById<TextView>(R.id.txtv_autor)
        txtViewAutor.setText(info)
        /////
        conn = ConexionSQLiteHelper(this)

        listViewLibros = findViewById(R.id.lv_libros)

        ListarLibros()

        var adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInfoLibros!!)
        listViewLibros!!.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listViewLibros)
        //////
    }

    fun ListarLibros(){
        var db: SQLiteDatabase = conn!!.readableDatabase

        var libros: Libros? = null
        listaLibros = ArrayList()
        //SELECT * FROM libros WHERE idAutorLibros = idAutor(intent)
        var cursor: Cursor = db.rawQuery("SELECT * FROM "+ SQL.TABLA_LIBROS+" WHERE "+SQL.COL_ID_AUTOR_LIBRO+"="+intent.getStringExtra("idAutor").toString(), null)

        while(cursor.moveToNext()){
            //const val COL_ID_LIBRO: String = "idLibro"
            //const val COL_NOMBRE_LIBRO: String = "nombreLibro"
            //const val COL_FECHA_SALIDA: String = "fecha_salida"
            //const val COL_PUNTUACION: String = "puntuacion"
            //const val COL_RECOMENDABLE: String = "recomendable"
            //const val COL_ID_AUTOR_LIBRO: String = "idAutorLibros"
            libros = Libros()
            libros.idLibro = cursor.getInt(0)
            libros.nombreLibro = cursor.getString(1)
            libros.fecha_salida = cursor.getString(2)
            libros.puntuacion = cursor.getDouble(3)
            libros.recomendable = cursor.getString(4)
            libros.idAutorLibros = cursor.getInt(5)

            listaLibros!!.add(libros)
        }
        obtenerLista()
    }

    fun obtenerLista(){
        listaInfoLibros = ArrayList()
        for(i  in 0 until listaLibros!!.size){
            listaInfoLibros!!.add("ID LIBRO: "+listaLibros!!.get(i).idLibro.toString()  + " \n "
                    + "NOMBRE LIBRO: "+ listaLibros!!.get(i).nombreLibro.toString() + " \n " +
                    "FECHA SALIDA: "+ listaLibros!!.get(i).fecha_salida + " \n " +
                    "PUNTUACION: "+ listaLibros!!.get(i).puntuacion+ " \n " +
                    "RECOMENDABLE: "+ listaLibros!!.get(i).recomendable+ " \n " +
                    "ID AUTOR CREADOR: "+ listaLibros!!.get(i).idAutorLibros)
        }
    }

    fun borrarLibro(id : Int){
        var db: SQLiteDatabase = conn!!.writableDatabase
        var parametros: Array<String> = arrayOf(listaLibros!!.get(id).idLibro.toString())
        db.delete(SQL.TABLA_LIBROS, SQL.COL_ID_LIBRO+"=?",parametros)
        Toast.makeText(this,"Se elimino el usuario con el id: "+listaLibros!!.get(id).idLibro.toString(),
            Toast.LENGTH_SHORT).show()
        db.close()
    }


    fun irActividad(clase: Class<*>){
        val intent2 = Intent(this, clase)
        intent2.putExtra("idAutorCrear",intent.getStringExtra("idAutor"))
        startActivity(intent2)
    }

    override fun onCreateContextMenu(menu: ContextMenu,
                                     v: View?,
                                     menuInfo: ContextMenu.ContextMenuInfo?){
        super.onCreateContextMenu(menu,v,menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menulibro, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_borrar -> {
                borrarLibro(idItemSeleccionado)
                recreate()
                return true
            }
            R.id.item_editar -> {
                val intent = Intent(this, actualizarLibro::class.java)
                intent.putExtra("idActualizarLibro", listaLibros!!.get(idItemSeleccionado).idLibro.toString())
                intent.putExtra("idAutorActualizar", this.intent.getStringExtra("idAutor").toString())
                startActivity(intent)
                Toast.makeText(this, "Selecciono editar este item ${idItemSeleccionado}", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}