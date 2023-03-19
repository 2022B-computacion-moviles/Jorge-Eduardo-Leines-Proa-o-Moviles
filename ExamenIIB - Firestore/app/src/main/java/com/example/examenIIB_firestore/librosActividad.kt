package com.example.examenIIB_firestore


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.examenIIB_firestore.entidades.Libros
import com.google.firebase.firestore.FirebaseFirestore

class librosActividad : AppCompatActivity() {
    var listViewLibros: ListView? = null
    var listaInfoLibros: ArrayList<String>? = null
    var listaLibros: ArrayList<Libros>? = null
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var idItemSeleccionado = 0
    override fun onRestart() {
        super.onRestart()
        recreate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libros_actividad)
        var info = intent.getStringExtra("infoId")
        var idAutor = intent.getStringExtra("idAutor")
        val botonCrearLibro = findViewById<Button>(R.id.btn_crear_libros_main)
        botonCrearLibro.setOnClickListener{
            irActividad(crearLibro::class.java)
        }
        val txtViewAutor = findViewById<TextView>(R.id.txtv_autor)
        txtViewAutor.setText(info)

        listaLibros = ArrayList()
        db.collection("Autor")
            .document(idAutor.toString())
            .collection("Libros")
            .get()
            .addOnSuccessListener {
                resultado ->
                for( libros in resultado){
                    var libro = Libros()
                    libro.idLibro = libros.id.toInt()
                    libro.idAutor = libros.get("idAutor").toString().toInt()
                    libro.NombreLibro = libros.get("NombreLibro").toString()
                    libro.Fecha_salida = libros.get("FechaSalida").toString()
                    libro.Puntuacion = libros.get("Puntuacion").toString().toDouble()
                    libro.Recomendable = libros.get("Recomendable").toString()
                    listaLibros!!.add(libro)
                }
                obtenerLista()

                listViewLibros = findViewById(R.id.lv_libros)

                var adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInfoLibros!!)
                listViewLibros!!.adapter = adaptador
                adaptador.notifyDataSetChanged()
                registerForContextMenu(listViewLibros)
            }
            .addOnFailureListener {
                Log.d("ERROR FIRESTORE", "NO SE CONECTO A LA BASE DE DATOS :(")
            }
    }

    fun obtenerLista(){
        listaInfoLibros = ArrayList()
        for(i  in 0 until listaLibros!!.size){
            listaInfoLibros!!.add("ID LIBRO: "+listaLibros!!.get(i).idLibro.toString()  + " \n "
                    + "NOMBRE LIBRO: "+ listaLibros!!.get(i).NombreLibro.toString() + " \n " +
                    "FECHA SALIDA: "+ listaLibros!!.get(i).Fecha_salida + " \n " +
                    "PUNTUACION: "+ listaLibros!!.get(i).Puntuacion+ " \n " +
                    "RECOMENDABLE: "+ listaLibros!!.get(i).Recomendable+ " \n " +
                    "ID AUTOR CREADOR: "+ listaLibros!!.get(i).idAutor)
        }
    }

    fun borrarLibro(id : Int){
        var idLibro = listaLibros!!.get(id).idLibro.toString()
        var idAutor = listaLibros!!.get(id).idAutor.toString()
        db.collection("Autor")
            .document(idAutor)
            .collection("Libros")
            .document(idLibro)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "El libro con el ID: ${idLibro} ha sido eliminado exisitosamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Ha ocurrido un error al eliminar al autor", Toast.LENGTH_SHORT).show()
            }
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