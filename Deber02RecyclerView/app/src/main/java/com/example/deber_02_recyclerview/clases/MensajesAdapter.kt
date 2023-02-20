package com.example.deber_02_recyclerview.clases

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_02_recyclerview.R

class MensajesAdapter(private val listaMensajes: ArrayList<Mensajes>) : RecyclerView.Adapter<MensajesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_men, parent, false)
        //Ahorra espacio si no existe una imagen en el mensaje
        Log.d("onCreateViewHolder", "ESTA HECHO")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNombre.setText(listaMensajes.get(position).Nombre)
        holder.foto.setImageResource(listaMensajes.get(position).ImagenId)
        holder.txtMensaje.setText(listaMensajes.get(position).mensajeText)
        holder.txtImagen.setImageResource(listaMensajes.get(position).mensajeImagen)
        if(listaMensajes.get(position).mensajeImagen == 0){
            holder.txtImagen.isGone = true
        }
        Log.d("onBindViewHolder", "ESTA HECHO")
    }

    override fun getItemCount(): Int {
        return listaMensajes.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var txtNombre: TextView = itemView.findViewById(R.id.UserId)
        var foto: ImageView = itemView.findViewById(R.id.idFoto)
        var txtMensaje: TextView = itemView.findViewById(R.id.idMensaje)
        var txtImagen: ImageView = itemView.findViewById(R.id.idMensajeFoto)
    }
}