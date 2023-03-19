package com.example.proyecto_ironbloodimages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class Login : Fragment() {
    var username: EditText? = null
    var password: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)
        username = view.findViewById(R.id.pt_username)
        password = view.findViewById(R.id.pt_pass)
        var login: Button = view.findViewById(R.id.btn_login)
        login.setOnClickListener {
            loginUser()
        }
        return view
    }

    private fun validateUsername(): Boolean? {
        val user: String = username!!.text.toString()
        return if (user.isEmpty()) {
            username!!.error = "No puede estar vacio"
            false
        } else {
            username!!.error = null
            true
        }
    }

    private fun validatePassword(): Boolean? {
        val pass: String = password!!.text.toString()
        return if (pass.isEmpty()) {
            password!!.error = "No puede estar vacio"
            false
        } else {
            password!!.error = null
            true
        }
    }

    fun loginUser() {
        //Validate Login Info
        if (!validateUsername()!! or !validatePassword()!!) {
            return
        } else {
            isUser()
        }
    }

    fun isUser() {
        var user = username!!.text.toString()
        var pass = password!!.text.toString()
        var db = FirebaseDatabase.getInstance()
        Log.d("USER", user)
        Log.d("PASS", pass)
        var checkUser = db.getReference("usuarios").orderByChild("nombre_usuario").equalTo(user)
        checkUser.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    username!!.error = null
                    var passwordFromDB = dataSnapshot.child(user).child("contrasenia").getValue().toString()
                    Log.d("passwordDB", passwordFromDB)
                    if (passwordFromDB!!.equals(pass)) {
                        username!!.error = null
                        var nombre = dataSnapshot.child(user).child("nombre_usuario").getValue().toString()
                        var email = dataSnapshot.child(user).child("email").getValue().toString()
                        var admin = "No"
                        if(dataSnapshot.child(user).child("isAdmin").exists()){
                            admin = dataSnapshot.child(user).child("isAdmin").getValue().toString()
                        }
                        var intent = Intent(context, MainActivity::class.java)
                        intent.putExtra("username", nombre)
                        intent.putExtra("email", email)
                        intent.putExtra("admin", admin)
                        startActivity(intent)
                        activity!!.finish()
                    } else {
                        password!!.error = "Contrasenia incorrecta"
                    }
                } else {
                    username!!.error = "El usuario no existe"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}