package com.example.examenIIB_firestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.examenIIB_firestore.entidades.Autor
import com.google.firebase.firestore.FirebaseFirestore

class autorActividad : AppCompatActivity() {

    var listViewAutor: ListView? = null
    var listaInfo: ArrayList<String>? = null
    var listaAutor: ArrayList<Autor>? = null
    var idItemSeleccionado = 0
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onRestart() {
        super.onRestart()
        recreate()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autoractividad)
        listaAutor = ArrayList()
        db.collection("Autor")
            .get()
            .addOnSuccessListener {
                resultado ->
                for ( autores in resultado){
                    var autor = Autor()
                    autor.idAutor = autores.id.toInt()
                    autor.Nombre = autores.get("Nombre")?.toString()
                    autor.Edad = autores.get("Edad") as Long?
                    autor.Valoracion = autores.get("Valoracion") as Double?
                    autor.FechaNacimiento = autores.get("FechaNacimiento")?.toString()
                    autor.Retirado = autores.get("Retirado")?.toString()
                    Log.d("AAAAAAAAAAAAAAAAAA", "${autor.Nombre}, ${autor.idAutor}, ${autor.Edad}, ${autor.Valoracion}, ${autor.FechaNacimiento}")
                    listaAutor!!.add(autor)
                }
                obtenerLista()
                listViewAutor = findViewById(R.id.lv_autor)
                var adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInfo!!)
                listViewAutor!!.adapter = adaptador
                adaptador.notifyDataSetChanged()
                registerForContextMenu(listViewAutor)
            }
            .addOnFailureListener {
                Log.d("ERROR FIRESTORE", "NO SE CONECTO A LA BASE DE DATOS :(")
            }
        val botonCrear = findViewById<Button>(R.id.btn_crear_autor_main)
        botonCrear.setOnClickListener{
            irActividad(actividadCrearAutor::class.java)
        }
    }

    fun obtenerLista(){
        listaInfo = ArrayList()
        for(i  in 0 until listaAutor!!.size){
            listaInfo!!.add("ID AUTOR: "+listaAutor!!.get(i).idAutor.toString()  + " \n "
                    + "NOMBRE AUTOR: "+listaAutor!!.get(i).Nombre.toString() + " \n " +
                    "EDAD: "+listaAutor!!.get(i).Edad + " \n " +
            "FECHA NACIMIENTO: "+ listaAutor!!.get(i).FechaNacimiento + " \n " +
            "VALORADO EN: "+listaAutor!!.get(i).Valoracion + " \n " +
            "RETIRADO?: "+listaAutor!!.get(i).Retirado
            )
        }
    }

    fun borrarActor(id : Int){
        var idAutor = listaAutor!!.get(id).idAutor.toString()
        db.collection("Autor").document(idAutor)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "El autor con el ID: ${idAutor} ha sido eliminado exisitosamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Ha ocurrido un error al eliminar al autor", Toast.LENGTH_SHORT).show()
            }
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
                var idAutor = listaAutor!!.get(idItemSeleccionado).idAutor.toString()
                intent.putExtra("idActualizar", idAutor)
                startActivity(intent)
                Toast.makeText(this, "Selecciono editar el autor con ID: ${idAutor}", Toast.LENGTH_SHORT).show()
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