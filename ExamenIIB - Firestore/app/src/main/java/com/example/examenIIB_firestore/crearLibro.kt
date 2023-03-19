package com.example.examenIIB_firestore

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import com.example.examenIIB_firestore.entidades.Libros
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class crearLibro : AppCompatActivity() {

    var mDisplayDate: TextView? = null
    var mDateSetListener: OnDateSetListener? = null
    var switchrecomendado: Switch? = null
    var campoidAutorLibros: EditText? = null
    var campoidLibro: EditText? = null
    var camponombreLibro: EditText? = null
    var campopuntuacion: EditText? = null
    var camporecomendable: String? = null
    var errorText: TextView? = null
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_libro)
        ///// POP UP INGRESAR FECHA
        popupfecha()
        /////
        campoidAutorLibros = findViewById(R.id.ptxt_idAutorLibro2)
        var valor = intent.getStringExtra("idAutorCrear")
        campoidAutorLibros!!.setText(valor)
        campoidAutorLibros!!.setEnabled(false)
        campoidLibro = findViewById(R.id.ptxt_idLibro2)
        camponombreLibro = findViewById(R.id.ptxt_nombreLibro2)
        campopuntuacion = findViewById(R.id.ptxt_puntuacion2)
        switchrecomendado = findViewById(R.id.swt_recomendado)
        errorText = findViewById(R.id.txt_error3)
        val botonCrearAutor = findViewById<Button>(R.id.btn_crear_libro2)
        botonCrearAutor.setOnClickListener {
            registrarLibro()
        }

    }

    fun registrarLibro(){
        if(switchrecomendado!!.isChecked){
            camporecomendable = switchrecomendado!!.textOn.toString()
        }
        else{
            camporecomendable = switchrecomendado!!.textOff.toString()
        }
        if(campoidLibro!!.text.isNotEmpty() && camponombreLibro!!.text.isNotEmpty() && campopuntuacion!!.text.isNotEmpty() && camporecomendable!!.isNotEmpty() && mDisplayDate!!.text.isNotEmpty()
            && campoidLibro!!.text.isDigitsOnly() && campoidAutorLibros!!.text.isDigitsOnly()){
            val libro = Libros(campoidAutorLibros!!.text.toString().toInt(),
                campoidLibro!!.text.toString().toInt(),
                camponombreLibro!!.text.toString(),
                mDisplayDate!!.text.toString(),
                campopuntuacion!!.text.toString().toDouble(),
                camporecomendable!!)
            val libro2 = hashMapOf(
                "NombreLibro" to libro.NombreLibro,
                "FechaSalida" to libro.Fecha_salida,
                "Recomendable" to libro.Recomendable,
                "Puntuacion" to libro.Puntuacion,
                "idAutor" to libro.idAutor
            )
            try{
                var db2 = db.collection("Autor").document(libro.idAutor.toString())
                    .collection("Libros").document(libro.idLibro.toString())
                db2.get().addOnSuccessListener {
                        resultado ->
                    if(!resultado.exists()){
                        db2.set(libro2)
                        errorText!!.isVisible = true
                        errorText!!.setTextColor(Color.parseColor("#008000"))
                        errorText!!.setText("Libro ingresado con exito")
                    }
                    else{
                        errorText!!.isVisible = true
                        errorText!!.setText("El id ya existe, escriba otro diferente")
                    }
                }
            }
            catch (error : Exception){
                errorText!!.isVisible = true
                errorText!!.setText(error.toString())
            }
        }
        else{
            errorText!!.isVisible = true
            errorText!!.setText("Todos los datos son obligatorios y deben ser llenados correctamente")
        }
    }

    fun popupfecha(){
        mDisplayDate = findViewById(R.id.textView9)
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
            OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month = month + 1
                Log.d(TAG, "onDateSet: mm/dd/yyy: $month/$day/$year")
                val date = "$month/$day/$year"
                mDisplayDate!!.setText(date)
            }
    }
}