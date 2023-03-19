package com.example.deber_02_recyclerview.fragmentos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_02_recyclerview.R
import com.example.deber_02_recyclerview.clases.Amigos
import com.example.deber_02_recyclerview.clases.AmigosAdapter
import com.example.deber_02_recyclerview.clases.Mensajes
import com.example.deber_02_recyclerview.clases.MensajesAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [mensajes.newInstance] factory method to
 * create an instance of this fragment.
 */
class mensajes : Fragment() {
    var listamensajes: ArrayList<Mensajes>? = arrayListOf()
    var recyclerMensajes: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var vista = inflater.inflate(R.layout.fragment_mensajes, container, false)
        recyclerMensajes = vista.findViewById(R.id.recyclerMensajes)
        recyclerMensajes!!.layoutManager = LinearLayoutManager(this.context)
        llenarLista()
        val mensajesAdap = MensajesAdapter(listamensajes!!)
        recyclerMensajes!!.adapter = mensajesAdap
        return vista
    }

    fun llenarLista(){
        listamensajes!!.add(Mensajes("MCX", R.drawable.f5c203e9708920b6adc4d789a6c48291, "bueeeeeeeeenas", 0))
        listamensajes!!.add(Mensajes("Obito", R.drawable.a26ef7d61a5d8354c9cfb6f92ce4ad2b, "Hola, mira", R.drawable.destiny2screenshot2023021416494612))
        listamensajes!!.add(Mensajes("MCX", R.drawable.f5c203e9708920b6adc4d789a6c48291, "Nice", 0))
        listamensajes!!.add(Mensajes("MCX", R.drawable.f5c203e9708920b6adc4d789a6c48291, "Yo me saque esto, checa", R.drawable.destiny2weapon))
        listamensajes!!.add(Mensajes("Obito", R.drawable.a26ef7d61a5d8354c9cfb6f92ce4ad2b, "Me parece cool", 0))
        listamensajes!!.add(Mensajes("MCX", R.drawable.f5c203e9708920b6adc4d789a6c48291, "che", 0))
        listamensajes!!.add(Mensajes("MCX", R.drawable.f5c203e9708920b6adc4d789a6c48291, "jugas osu tambien", 0))
        listamensajes!!.add(Mensajes("Obito", R.drawable.a26ef7d61a5d8354c9cfb6f92ce4ad2b, "No sorry", 0))
        listamensajes!!.add(Mensajes("MCX", R.drawable.f5c203e9708920b6adc4d789a6c48291, "prqqqqqq no dea xd?", 0))
        listamensajes!!.add(Mensajes("Obito", R.drawable.a26ef7d61a5d8354c9cfb6f92ce4ad2b, "me aburre\n" +
                "perdon xd", 0))
        listamensajes!!.add(Mensajes("MCX", R.drawable.f5c203e9708920b6adc4d789a6c48291, "jaajj xd, ya ni modo", 0))
        listamensajes!!.add(Mensajes("MCX", R.drawable.f5c203e9708920b6adc4d789a6c48291, "si me salio", R.drawable.genshin))
        listamensajes!!.add(Mensajes("Obito", R.drawable.a26ef7d61a5d8354c9cfb6f92ce4ad2b, "tremenda suerte, a mi no xd", 0))
    }
}