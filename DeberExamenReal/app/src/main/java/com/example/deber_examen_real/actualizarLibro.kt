package com.example.deber_examen_real

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.example.deber_examen_real.basedatos.ConexionSQLiteHelper
import com.example.deber_examen_real.basedatos.SQL
import java.util.*

class actualizarLibro : AppCompatActivity() {
    var mDisplayDate: TextView? = null
    var mDateSetListener: OnDateSetListener? = null
    var switchRecomendable: Switch? = null
    var campoidAutorLibros: EditText? = null
    var campoidLibro: EditText? = null
    var camponombreLibro: EditText? = null
    var campopuntuacion: EditText? = null
    var camporecomendable: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_libro)
        //////
        popupfecha()
        //////
        campoidAutorLibros = findViewById(R.id.ptxt_idAutorLibro2)
        var valor = intent.getStringExtra("idAutorActualizar")
        campoidAutorLibros!!.setText(valor)
        campoidAutorLibros!!.setEnabled(false)
        campoidLibro = findViewById(R.id.ptxt_idLibro2)
        var valor2 = intent.getStringExtra("idActualizarLibro")
        campoidLibro!!.setText(valor2)
        campoidLibro!!.setEnabled(false)
        camponombreLibro = findViewById(R.id.ptxt_nombreLibro2)
        campopuntuacion = findViewById(R.id.ptxt_puntuacion2)
        switchRecomendable = findViewById(R.id.swt_recomendable_act)
        ////
        val botonCrearAutor = findViewById<Button>(R.id.btn_crear_libro2)
        botonCrearAutor.setOnClickListener {
            actualizarLibro()
        }

    }

    fun actualizarLibro(){
        var conn = ConexionSQLiteHelper(this)
        var db: SQLiteDatabase = conn.writableDatabase
        var parametros: Array<String> = arrayOf(campoidLibro!!.text.toString())

        var values = ContentValues()
        if(switchRecomendable!!.isChecked){
            camporecomendable = switchRecomendable!!.textOn.toString()
        }
        else{
            camporecomendable = switchRecomendable!!.textOff.toString()
        }
        values.put(SQL.COL_ID_AUTOR_LIBRO,campoidAutorLibros?.text.toString())
        values.put(SQL.COL_NOMBRE_LIBRO,camponombreLibro?.text.toString())
        values.put(SQL.COL_FECHA_SALIDA,mDisplayDate?.text.toString())
        values.put(SQL.COL_PUNTUACION,campopuntuacion?.text.toString())
        values.put(SQL.COL_RECOMENDABLE,camporecomendable)

        db.update(SQL.TABLA_LIBROS,values,SQL.COL_ID_LIBRO+"=?",parametros)

        Toast.makeText(this, "LIBRO ID: ${campoidLibro!!.text.toString()} actualizado", Toast.LENGTH_SHORT).show()
        db.close()
    }

    fun popupfecha(){
        mDisplayDate = findViewById(R.id.txt_fecha_salida)
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
                Log.d(ContentValues.TAG, "onDateSet: mm/dd/yyy: $month/$day/$year")
                val date = "$month/$day/$year"
                mDisplayDate!!.setText(date)
            }
    }
}