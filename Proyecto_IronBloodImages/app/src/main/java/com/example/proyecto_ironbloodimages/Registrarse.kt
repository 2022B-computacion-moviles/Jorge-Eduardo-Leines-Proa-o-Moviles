package com.example.proyecto_ironbloodimages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.proyecto_ironbloodimages.entidades.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Registrarse : Fragment() {
    lateinit var ptNombre: EditText
    lateinit var ptApellido: EditText
    lateinit var ptNombreUsuario: EditText
    lateinit var ptEmail: EditText
    lateinit var ptPass: EditText
    lateinit var ptPassConf: EditText
    var user = Usuario()
    var db = FirebaseDatabase.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_registrarse, container, false)
        ptNombre = view.findViewById(R.id.pt_name_re)
        ptApellido = view.findViewById(R.id.pt_apellido_re)
        ptNombreUsuario = view.findViewById(R.id.pt_username_re)
        ptEmail = view.findViewById(R.id.pt_email_re)
        ptPass = view.findViewById(R.id.pt_pass_re)
        ptPassConf = view.findViewById(R.id.pt_passCon_re)
        var btnRegistrar = view.findViewById<Button>(R.id.btn_registrarse)
        btnRegistrar.setOnClickListener {
            registrarUsuario()
        }
        return view
    }

    fun registrarUsuario(){
        if(ptNombre.text.isNotEmpty()){
            user.nombre = ptNombre.text.toString()
            if(ptApellido.text.isNotEmpty()){
                user.apellido = ptApellido.text.toString()
                if(ptNombreUsuario.text.isNotEmpty()){
                    user.nombre_usuario = ptNombreUsuario.text.toString()
                    if(ptEmail.text.isNotEmpty()){
                        user.email = ptEmail.text.toString()
                        if(ptPass.text.isNotEmpty()){
                            user.contrasenia = ptPass.text.toString()
                            if(ptPassConf.text.isNotEmpty()){
                                if(ptPass.text.toString() == ptPassConf.text.toString()){
                                    comprobarUsuario()
                                }
                                else{
                                    ptPassConf.error = "La contrasenia ingresada no coincide"
                                }
                            }
                            else{
                                ptPassConf.error = "Vuelva a escribir su contrasenia"
                            }
                        }
                        else{
                            ptPass.error = "Ingrese su contrasenia"
                        }
                    }
                    else{
                        ptEmail.error = "Escriba un correo electronico"
                    }
                }
                else{
                    ptNombreUsuario.error = "Escriba su nombre de usuario"
                }
            }
            else{
                ptApellido.error = "Escriba un apellido"
            }
        }
        else{
            ptNombre.error = "Debe ingresar el nombre"
        }


    }

    fun comprobarUsuario(){
        var checkUserName = db.getReference("usuarios").child(user.nombre_usuario.toString())
        checkUserName.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    ptNombreUsuario.error = "Este nombre de usuario ya existe"
                }
                else{
                    db.getReference("usuarios").child(user.nombre_usuario.toString()).setValue(user)
                    Toast.makeText(context, "Se registro su usuario exitosamente, puede regresar y logearse", Toast.LENGTH_LONG).show()
                    ptNombre.text.clear()
                    ptApellido.text.clear()
                    ptNombreUsuario.text.clear()
                    ptEmail.text.clear()
                    ptPass.text.clear()
                    ptPassConf.text.clear()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}