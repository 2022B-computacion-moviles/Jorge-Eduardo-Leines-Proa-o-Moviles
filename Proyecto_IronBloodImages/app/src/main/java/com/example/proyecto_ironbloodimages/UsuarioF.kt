package com.example.proyecto_ironbloodimages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue

class UsuarioF : Fragment() {
    lateinit var user: String
    lateinit var userT: TextView
    lateinit var nombreT: TextView
    lateinit var lastNT: TextView
    lateinit var emailT: TextView
    var db = FirebaseDatabase.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_usuario, container, false)
        user = arguments?.getString("user").toString()
        Log.d("INFO_USER", user)
        userT = view.findViewById(R.id.user_name)
        nombreT = view.findViewById(R.id.user_nombre)
        lastNT = view.findViewById(R.id.user_apellido)
        emailT = view.findViewById(R.id.user_email)
        db.getReference("usuarios").child(user).get().addOnSuccessListener {
            resultado->
            Log.d("USER", resultado.key.toString())
            userT.setText(resultado.key.toString())
            nombreT.setText(resultado.child("nombre").getValue().toString())
            lastNT.setText(resultado.child("apellido").getValue().toString())
            emailT.setText(resultado.child("email").getValue().toString())
        }
        return view
    }
}