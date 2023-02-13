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

class autorActividad : AppCompatActivity() {
    var listViewAutor: ListView? = null
    var listaInfo: ArrayList<String>? = null
    var listaAutor: ArrayList<Autor>? = null
    var conn: ConexionSQLiteHelper? = null
    var idItemSeleccionado = 0

    override fun onRestart() {
        super.onRestart()
        recreate()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autoractividad)

        val botonCrear = findViewById<Button>(R.id.btn_crear_autor_main)
        botonCrear.setOnClickListener{
            irActividad(actividadCrearAutor::class.java)
        }

        /////
        conn = ConexionSQLiteHelper(this)

        listViewAutor = findViewById(R.id.lv_autor)

        ListarAutor()

        var adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInfo!!)
        listViewAutor!!.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listViewAutor)
        //////
    }

    fun ListarAutor(){
        var db: SQLiteDatabase = conn!!.readableDatabase

        var autor: Autor? = null
        listaAutor = ArrayList()
        //SELECT * FROM AUTOR
        var cursor: Cursor = db.rawQuery("SELECT * FROM "+ SQL.TABLA_AUTOR, null)

        while(cursor.moveToNext()){
            autor = Autor()
            autor.idAutor = cursor.getInt(0)
            autor.nombre = cursor.getString(1)
            autor.edad = cursor.getInt(2)
            autor.valoracion = cursor.getDouble(3)
            autor.fechanacimiento = cursor.getString(4)
            autor.retirado = cursor.getString(5)

            listaAutor!!.add(autor)
        }
        obtenerLista()
    }

    fun obtenerLista(){
        listaInfo = ArrayList()
        for(i  in 0 until listaAutor!!.size){
            listaInfo!!.add("ID AUTOR: "+listaAutor!!.get(i).idAutor.toString()  + " \n "
                    + "NOMBRE AUTOR: "+listaAutor!!.get(i).nombre.toString() + " \n " +
                    "EDAD: "+listaAutor!!.get(i).edad + " \n " +
            "FECHA NACIMIENTO: "+ listaAutor!!.get(i).fechanacimiento+ " \n " +
            "VALORADO EN: "+listaAutor!!.get(i).valoracion+ " \n " +
            "RETIRADO?: "+listaAutor!!.get(i).retirado)
        }
    }

    fun borrarActor(id : Int){
        var db: SQLiteDatabase = conn!!.writableDatabase
        var parametros: Array<String> = arrayOf(listaAutor!!.get(id).idAutor.toString())
        db.delete(SQL.TABLA_AUTOR,SQL.COL_IDAUTOR+"=?",parametros)
        Toast.makeText(this,"Se elimino el usuario con el id: "+listaAutor!!.get(id).idAutor.toString(),Toast.LENGTH_SHORT).show()
        db.close()
    }


    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
    override fun onCreateContextMenu(menu: ContextMenu,
                                     v: View?,
                                     menuInfo: ContextMenu.ContextMenuInfo?){
        super.onCreateContextMenu(menu,v,menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menuautor, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_borrar -> {
                borrarActor(idItemSeleccionado)
                recreate()
                return true
            }
            R.id.item_editar -> {
                val intent = Intent(this, actividadActualizarAutor::class.java)
                intent.putExtra("idActualizar", listaAutor!!.get(idItemSeleccionado).idAutor.toString())
                startActivity(intent)
                Toast.makeText(this, "Selecciono editar este item ${idItemSeleccionado}", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.item_revisar -> {
                val intent = Intent(this, librosActividad::class.java)
                intent.putExtra("idAutor", listaAutor!!.get(idItemSeleccionado).idAutor.toString())
                intent.putExtra("infoId", listaInfo!!.get(idItemSeleccionado))
                startActivity(intent)
                Toast.makeText(this, "Selecciono revisar este item ${idItemSeleccionado}", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}