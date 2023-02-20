package com.example.deber_02_recyclerview.clases

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_02_recyclerview.R


class AmigosAdapter(private val listaAmigos: ArrayList<Amigos>) : RecyclerView.Adapter<AmigosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        Log.d("onCreateViewHolder", "ESTA HECHO")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNombre.setText(listaAmigos.get(position).Nombre)
        holder.foto.setImageResource(listaAmigos.get(position).ImagenId)
        holder.txtEstado.setText(listaAmigos.get(position).estado)
        Log.d("onBindViewHolder", "ESTA HECHO")
    }

    override fun getItemCount(): Int {
        return listaAmigos.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var txtNombre: TextView = itemView.findViewById(R.id.idNombre)
        var foto: ImageView = itemView.findViewById(R.id.idImagen)
        var txtEstado: TextView = itemView.findViewById(R.id.estadoId)
    }
}