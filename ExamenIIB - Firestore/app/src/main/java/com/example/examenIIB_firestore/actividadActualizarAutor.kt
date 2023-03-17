package com.example.examenIIB_firestore

import android.app.DatePickerDialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import com.example.examenIIB_firestore.entidades.Autor
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class actividadActualizarAutor : AppCompatActivity() {

    var mDisplayDate: TextView? = null
    var mDateSetListener: DatePickerDialog.OnDateSetListener? = null
    var switchretirado: Switch? = null
    var campoId: EditText? = null
    var campoNombre: EditText? = null
    var campoEdad: EditText? = null
    var campoValoracion: EditText? = null
    var campoRetirado: String? = null
    var txtError: TextView? = null
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_actualizar_autor)
        /////
        popupfecha()
        /////
        campoId = findViewById(R.id.ptxt_id_autor2)
        var valor = intent.getStringExtra("idActualizar")
        campoId!!.setText(valor)
        campoId!!.setEnabled(false)
        campoNombre = findViewById(R.id.ptxt_nombre2)
        campoEdad = findViewById(R.id.ptxt_edad2)
        campoValoracion = findViewById(R.id.ptxt_valoracion2)
        switchretirado = findViewById(R.id.swt_retirado2)
        txtError = findViewById(R.id.txt_error2)
        val botonCrearAutor = findViewById<Button>(R.id.btn_editar_autor)
        botonCrearAutor.setOnClickListener {
            actualizarAutor()
        }
    }

    fun actualizarAutor(){
        if(switchretirado!!.isChecked){
            campoRetirado = switchretirado!!.textOn.toString()
        }
        else{
            campoRetirado = switchretirado!!.textOff.toString()
        }

        if(campoId!!.text.isNotEmpty() && campoNombre!!.text.isNotEmpty() && campoEdad!!.text.isNotEmpty() && campoValoracion!!.text.isNotEmpty() && mDisplayDate!!.text.isNotEmpty()
            && campoId!!.text.isDigitsOnly() && campoEdad!!.text.isDigitsOnly()){
            val autor = Autor(campoId!!.text.toString().toInt(),
                campoNombre!!.text.toString(),
                campoEdad!!.text.toString().toLong(),
                campoValoracion!!.text.toString().toDouble(),
                mDisplayDate!!.text.toString(),
                campoRetirado!!)
            val autor2 = hashMapOf(
                "Nombre" to autor.Nombre,
                "Edad" to autor.Edad,
                "Valoracion" to autor.Valoracion,
                "FechaNacimiento" to autor.FechaNacimiento,
                "Retirado" to autor.Retirado
            )
            try{
                db.collection("Autor")
                    .document(autor.idAutor.toString())
                    .set(autor2)
                    .addOnSuccessListener {
                        txtError!!.isVisible = true
                        txtError!!.setTextColor(Color.parseColor("#008000"))
                        txtError!!.setText("Autor (ID: ${autor.idAutor}) actualizado con exito")
                    }
                    .addOnFailureListener {
                        txtError!!.setText("Error al actualizar el autor")
                    }
            }
            catch (error : Exception){
                txtError!!.isVisible = true
                txtError!!.setText(error.toString())
            }
        }
        else{
            txtError!!.isVisible = true
            txtError!!.setText("Todos los datos son obligatorios y deben ser llenados correctamente para actualizar")
        }
    }

    fun popupfecha(){
        mDisplayDate = findViewById(R.id.txt_nacimiento2)
        mDisplayDate!!.setOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val year: Int = cal.get(Calendar.YEAR)
            val month: Int = cal.get(Calendar.MONTH)
            val day: Int = cal.get(Calendar.DAY_OF_MONTH)
            val dialog = DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        mDateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month = month + 1
                Log.d(ContentValues.TAG, "onDateSet: mm/dd/yyy: $month/$day/$year")
                val date = "$month/$day/$year"
                mDisplayDate!!.setText(date)
            }
    }

}