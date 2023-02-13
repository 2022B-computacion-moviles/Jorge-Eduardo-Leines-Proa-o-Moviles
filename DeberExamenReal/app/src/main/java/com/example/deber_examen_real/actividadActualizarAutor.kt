package com.example.deber_examen_real

import android.app.DatePickerDialog
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.deber_examen_real.basedatos.ConexionSQLiteHelper
import com.example.deber_examen_real.basedatos.SQL
import java.util.*

class actividadActualizarAutor : AppCompatActivity() {

    var mDisplayDate: TextView? = null
    var mDateSetListener: DatePickerDialog.OnDateSetListener? = null
    var switchnacimiento: Switch? = null
    var campoId: EditText? = null
    var campoNombre: EditText? = null
    var campoEdad: EditText? = null
    var campoValoracion: EditText? = null
    var campoRetirado: String? = null
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
        switchnacimiento = findViewById(R.id.swt_retirado2)
        val botonCrearAutor = findViewById<Button>(R.id.btn_editar_autor)
        botonCrearAutor.setOnClickListener {
            actualizarAutor()
        }
    }

    fun actualizarAutor(){
        var conn = ConexionSQLiteHelper(this)
        var db: SQLiteDatabase = conn.writableDatabase
        var parametros: Array<String> = arrayOf(campoId!!.text.toString())
        if(switchnacimiento!!.isChecked){
            campoRetirado = switchnacimiento!!.textOn.toString()
        }
        else{
            campoRetirado = switchnacimiento!!.textOff.toString()
        }
        var values = ContentValues()
        values.put(SQL.COL_NOMBRE,campoNombre?.text.toString())
        values.put(SQL.COL_EDAD,campoEdad?.text.toString())
        values.put(SQL.COL_VALORACION,campoValoracion?.text.toString())
        values.put(SQL.COL_FECHA_NACIMIENTO,mDisplayDate!!.text.toString())
        values.put(SQL.COL_RETIRADO,campoRetirado!!)

        db.update(SQL.TABLA_AUTOR,values,SQL.COL_IDAUTOR+"=?",parametros)

        Toast.makeText(this,"Se actualizo el libro con exito ", Toast.LENGTH_SHORT).show()
        db.close()
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