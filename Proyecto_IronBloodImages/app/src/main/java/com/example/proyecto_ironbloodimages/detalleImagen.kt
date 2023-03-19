package com.example.proyecto_ironbloodimages

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.File


class detalleImagen : AppCompatActivity() {
    var tv_nombre: TextView? = null
    var iv_imagen: ImageView? = null
    var tv_detalle: TextView? = null
    var tv_cate: TextView? = null
    var swt_fav: Switch? = null
    var btn_borrar: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_imagen)
        swt_fav = findViewById(R.id.swt_favorito)
        tv_nombre = findViewById(R.id.tv_nombre_det)
        iv_imagen = findViewById(R.id.image_detalle)
        tv_detalle = findViewById(R.id.tv_detalle_det)
        tv_cate = findViewById(R.id.tv_categorias_det)
        btn_borrar = findViewById(R.id.btn_borrar)

        val btn_descargar = findViewById<Button>(R.id.btn_buy_download)
        var imagen_rute = intent.getStringExtra("imagenRuta")
        var nombre = intent.getStringExtra("nombre")
        cargarInfo()
        esFavorito()
        esAdmin()

        swt_fav!!.setOnClickListener {
            estaLogeado()
        }

        btn_borrar!!.setOnClickListener {
            borrar()
            finish()
        }
        btn_descargar.setOnClickListener {
            descargar(nombre!!, imagen_rute!!)
        }

    }

    fun cargarInfo(){
        var nombre = intent.getStringExtra("nombre")
        var detail = intent.getStringExtra("descripcion")
        var categorias = intent.getStringExtra("categorias")
        var imagen_rute = intent.getStringExtra("imagenRuta")

        tv_nombre!!.setText(nombre)
        tv_detalle!!.setText(detail)
        tv_cate!!.setText(categorias)
        Picasso.get().load(imagen_rute).into(iv_imagen)
    }

    fun descargar(filename:String, url: String){
        try {
            val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri: Uri = Uri.parse(url)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + filename.toString() + ".jpg"
                )
            dm.enqueue(request)
            Toast.makeText(this, "Su imagen se esta descargando, puede verla en la barra de notificaciones", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Su imagen fallo en descargarse", Toast.LENGTH_SHORT).show()
        }
    }

    fun estaLogeado(){
        var user = intent.getStringExtra("user").toString()
        var idImg = intent.getStringExtra("id")!!.toInt()
        if(user == "null"){
            if(swt_fav!!.isChecked){
                Toast.makeText(this, "Ingrese con un usuario para anadir a favoritos", Toast.LENGTH_LONG).show()
                swt_fav!!.isChecked=false
            }
        }
        else{
            var db = FirebaseDatabase.getInstance()
            if(swt_fav!!.isChecked){
                db.getReference("usuarios").child(user!!).child("favoritos").child("id ${idImg}").setValue(idImg)
                Toast.makeText(this, "Imagen ingresada a favoritos", Toast.LENGTH_LONG).show()
            }
            else{
                db.getReference("usuarios").child(user!!).child("favoritos").child("id ${idImg}").removeValue()
                Toast.makeText(this, "Se quito de favoritos", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun esFavorito(){
        var user = intent.getStringExtra("user").toString()
        var idImg = intent.getStringExtra("id")!!.toInt()
        var db = FirebaseDatabase.getInstance()
        db.getReference("usuarios").child(user).child("favoritos").child("id ${idImg}").get().addOnSuccessListener {
            resultado->
            Log.d("RESULTADOS", resultado.getValue().toString())
            Log.d("RESULTADOS BOOLEAN", resultado.exists().toString())
            if(resultado.exists()){
                Log.d("RESULTADOS BOOLEAN", resultado.exists().toString())
                swt_fav!!.isChecked = true
            }
        }
    }
    fun esAdmin(){
        var user = intent.getStringExtra("user").toString()
        var admin = intent.getStringExtra("admin").toString()
        Log.d("ADMIN", admin)
        Log.d("USER ADMIN", admin)
        if(user == "null" || admin == "null" || admin == "No"){
            btn_borrar!!.isGone = true
        }
        else{
            btn_borrar!!.isVisible = true
        }
    }

    fun borrar(){
        var idImg = intent.getStringExtra("id").toString()
        var db = FirebaseDatabase.getInstance().getReference("producto_imagen")
        var storage = FirebaseStorage.getInstance().getReference("imagenes")

        db.child(idImg).removeValue()
        storage.child(idImg).delete()

    }
}