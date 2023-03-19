package com.example.proyecto_ironbloodimages

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.proyecto_ironbloodimages.entidades.Producto_imagen
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class Agregar : Fragment() {
    private var storageReference = FirebaseStorage.getInstance().getReference("imagenes")
    private var db = FirebaseDatabase.getInstance()
        .getReference("producto_imagen")
    private val pickImage = 100
    private var imageUri: Uri? = null
    private lateinit var imageView: ImageView
    private lateinit var progreso: TextView
    private lateinit var guardar: Button
    private lateinit var idET: EditText
    private lateinit var nombreET: EditText
    private lateinit var descripcionET: EditText
    private lateinit var cateET: EditText
    private var imagen = Producto_imagen()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_agregar, container, false)
        imageView = view.findViewById(R.id.img_select)
        progreso = view.findViewById(R.id.tv_progreso)
        guardar = view.findViewById(R.id.btn_agregar)
        idET = view.findViewById(R.id.id_agregar)
        nombreET = view.findViewById(R.id.nombre_agregar)
        descripcionET = view.findViewById(R.id.desc_agregar)
        cateET = view.findViewById(R.id.cate_agregar)
        guardar.setOnClickListener {
            guardarBaseDatos()
        }
        imageView.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        return view
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

    private fun guardarBaseDatos(){

        if(idET.text.isNotEmpty()){
            if(idET.text.isDigitsOnly()){
                imagen.id = idET.text.toString().toInt()
                if(nombreET.text.isNotEmpty()){
                    imagen.nombre = nombreET.text.toString()
                    if(descripcionET.text.isNotEmpty()){
                        imagen.descripcion = descripcionET.text.toString()
                        if(cateET.text.isNotEmpty()){
                            imagen.categoria = cateET.text.toString()
                            if(imageUri != null){
                                subirImagen(imagen.id.toString())
                            }
                            else{
                                progreso.isVisible = true
                                progreso.setText("Selecciona una imagen")
                            }
                        }
                        else{
                            cateET.error = "Ingrese una categoria"
                        }
                    }
                    else{
                        descripcionET.error = "Ingrese una descripcion"
                    }
                }
                else{
                    nombreET.error = "Ingrese un nombre"
                }
            }
            else{
                idET.error = "Ingrese solo digitos"
            }
        }
        else{
            idET.error = "Ingrese un id"
        }

    }

    private fun subirImagen(id:String){
        val storageRef =  storageReference.child(id)
        storageRef.downloadUrl.addOnSuccessListener {
            progreso.text = "Existe ya esta imagen con ese id"
        }
            .addOnFailureListener {
                if(imageUri  != null) {
                    storageRef.putFile(imageUri!!)
                        .addOnProgressListener {
                            val progresoInt = (100 * it.bytesTransferred/it.totalByteCount).toDouble()
                            progreso.isVisible = true
                            progreso.text = "Subiendo: ${progresoInt} %"
                        }
                        .addOnCompleteListener{

                        }
                        .addOnSuccessListener {
                            Toast.makeText(context, "Imagen subida y guardada, si existe un mismo id se borrara", Toast.LENGTH_SHORT).show()
                            progreso.text = "Imagen subida"
                            guardarFirebaseDatabase()
                        }
                        .addOnFailureListener{
                            Toast.makeText(context, "Se produjo un error al subir la imagen", Toast.LENGTH_SHORT).show()
                        }
                }
            }
    }


    private fun guardarFirebaseDatabase(){
        var checkImage = db.child(imagen.id.toString())
        checkImage.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    idET.error = "La imagen con ID ${snapshot.key} ya existe"
                    storageReference.child(snapshot.key.toString()).delete()
                }
                else{
                    var storageRef = storageReference.child(imagen.id.toString())
                    storageRef.downloadUrl.addOnSuccessListener {
                        imagen.imagen_rute= it.toString()
                        db.child(imagen.id.toString()).setValue(imagen)
                    }

                    imageUri = null
                    imageView.setImageResource(R.drawable.ic_baseline_add_a_photo_24)
                    idET.text.clear()
                    nombreET.text.clear()
                    descripcionET.text.clear()
                    cateET.text.clear()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
//    var checkUserName = db.getReference("usuarios").child(user.nombre_usuario.toString())
//    checkUserName.addListenerForSingleValueEvent(object: ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            if(dataSnapshot.exists()){
//                ptNombreUsuario.error = "Este nombre de usuario ya existe"
//            }
//            else{
//                db.getReference("usuarios").child(user.nombre_usuario.toString()).setValue(user)
//                Toast.makeText(context, "Se registro su usuario exitosamente, puede regresar y logearse", Toast.LENGTH_LONG).show()
//                ptNombre.text.clear()
//                ptApellido.text.clear()
//                ptNombreUsuario.text.clear()
//                ptEmail.text.clear()
//                ptPass.text.clear()
//                ptPassConf.text.clear()
//
//            }
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//            TODO("Not yet implemented")
//        }
//    })

}