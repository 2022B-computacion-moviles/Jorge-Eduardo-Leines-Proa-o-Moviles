package com.example.proyecto_ironbloodimages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_ironbloodimages.adaptadores.ImagenAdapter
import com.example.proyecto_ironbloodimages.entidades.Producto_imagen
import com.google.firebase.database.FirebaseDatabase

class Inicio : Fragment() {
    var recyclerImagenes: RecyclerView? = null
    var listaImagenes: ArrayList<Producto_imagen> = arrayListOf()
    lateinit var imagenAdap: ImagenAdapter
    lateinit var user : String
    lateinit var admin: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var vista = inflater.inflate(R.layout.fragment_inicio, container, false)
        recyclerImagenes = vista.findViewById(R.id.recyclerImagenes2)
        recyclerImagenes!!.layoutManager = LinearLayoutManager(context)
        getImagenes()
        return vista
    }

    fun getImagenes(){
        var db: FirebaseDatabase = FirebaseDatabase.getInstance()
        user = arguments?.getString("user").toString()
        admin = arguments?.getString("admin").toString()
        db.getReference("producto_imagen").get().addOnSuccessListener{
                resultado->
            for(imagen in resultado.children.toList()){
                var producto = Producto_imagen()
                producto.id = imagen.key.toString().toInt()
                producto.nombre = imagen.child("nombre").getValue().toString()
                producto.imagen_rute = imagen.child("imagen_rute").getValue().toString()
                producto.categoria = imagen.child("categoria").getValue().toString()
                producto.descripcion = imagen.child("descripcion").getValue().toString()
                Log.d("DATO", producto.toString())
                listaImagenes.add(producto)
            }
            imagenAdap = ImagenAdapter(listaImagenes, user, admin)
            imagenAdap.notifyDataSetChanged()
            Log.d("SE ENVIO EL ADAPTER","ADAPTER HECHO")
            recyclerImagenes!!.adapter = imagenAdap
        }
    }

}