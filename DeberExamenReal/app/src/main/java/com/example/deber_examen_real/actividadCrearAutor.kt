package com.example.deber_examen_real

import android.app.DatePickerDialog
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.deber_examen_real.basedatos.ConexionSQLiteHelper
import com.example.deber_examen_real.basedatos.SQL
import java.util.*

class actividadCrearAutor : AppCompatActivity() {
    var mDisplayDate: TextView? = null
    var mDateSetListener: DatePickerDialog.OnDateSetListener? = null
    var switchretirado: Switch? = null
    var campoId: EditText? = null
    var campoNombre: EditText? = null
    var campoEdad: EditText? = null
    var campoValoracion: EditText? = null
    var campoRetirado: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_crear_autor)
        ////
        popupfecha()
        ////
        campoId = findViewById(R.id.ptxt_id_autor)
        campoId!!.setText("GENERADO AUTO")
        campoId!!.setEnabled(false)
        campoNombre = findViewById(R.id.ptxt_nombre)
        campoEdad = findViewById(R.id.ptxt_edad)
        campoValoracion = findViewById(R.id.ptxt_valoracion)
        switchretirado = findViewById(R.id.swt_retirado)
        val botonCrearAutor = findViewById<Button>(R.id.btn_crear_autor)
        botonCrearAutor.setOnClickListener {
            registrarAutor()
        }

    }

    fun registrarAutor(){
        var conn = ConexionSQLiteHelper(this)
        var db: SQLiteDatabase = conn.writableDatabase
        var values = ContentValues()
        if(switchretirado!!.isChecked){
            campoRetirado = switchretirado!!.textOn.toString()
        }
        else{
            campoRetirado = switchretirado!!.textOff.toString()
        }
        values.put(SQL.COL_NOMBRE,campoNombre?.text.toString())
        values.put(SQL.COL_EDAD,campoEdad?.text.toString())
        values.put(SQL.COL_VALORACION,campoValoracion?.text.toString())
        values.put(SQL.COL_FECHA_NACIMIENTO,mDisplayDate!!.text.toString())
        values.put(SQL.COL_RETIRADO,campoRetirado!!)

        var idResultante: Long = db.insert(SQL.TABLA_AUTOR,SQL.COL_IDAUTOR,values)

        Toast.makeText(this,"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show()
        db.close()
    }

    fun popupfecha(){
        mDisplayDate = findViewById(R.id.txt_nacimiento)
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