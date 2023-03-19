package com.example.proyecto_ironbloodimages.adaptadores

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_ironbloodimages.R
import com.example.proyecto_ironbloodimages.databinding.ItemListBinding
import com.example.proyecto_ironbloodimages.detalleImagen
import com.example.proyecto_ironbloodimages.entidades.Producto_imagen
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class ImagenAdapter(private var listaImagenes: ArrayList<Producto_imagen>, private var user: String, private var admin: String) : RecyclerView.Adapter<ImagenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        Log.d("onCreateViewHolder", "ESTA HECHO")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNombre.setText(listaImagenes.get(position).nombre)
        with(listaImagenes.get(position).imagen_rute.toString()){
            Picasso.get().load(this).into(holder.imagen)
        }
        holder.tvCategoria.setText(listaImagenes.get(position).categoria)
        holder.itemView.setOnClickListener{
            Log.d("userAdap",user)
            var intent = Intent(holder.itemView.context, detalleImagen::class.java)
            intent.putExtra("nombre", listaImagenes.get(position).nombre)
            intent.putExtra("descripcion", listaImagenes.get(position).descripcion)
            intent.putExtra("categorias", listaImagenes.get(position).categoria)
            intent.putExtra("imagenRuta", listaImagenes.get(position).imagen_rute)
            intent.putExtra("id",listaImagenes.get(position).id.toString())
            intent.putExtra("user", user)
            intent.putExtra("admin", admin)
            Log.d("ID", listaImagenes.get(position).id.toString())
            holder.itemView.context.startActivity(intent)
        }
        Log.d("onBindViewHolder", "ESTA HECHO")
    }

    override fun getItemCount(): Int {
        return listaImagenes.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var tvNombre: TextView = itemView.findViewById(R.id.nombre)
        var imagen: ImageView = itemView.findViewById(R.id.imagen)
        var tvCategoria: TextView = itemView.findViewById(R.id.categorias)
    }
}