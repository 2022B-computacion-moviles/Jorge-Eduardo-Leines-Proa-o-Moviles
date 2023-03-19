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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [amigos.newInstance] factory method to
 * create an instance of this fragment.
 */
class amigos : Fragment() {

    var listaAmigos: ArrayList<Amigos>? = arrayListOf()
    var recyclerAmigos: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var vista = inflater.inflate(R.layout.fragment_amigos, container, false)
        recyclerAmigos = vista.findViewById(R.id.recyclerAmigos)
        recyclerAmigos!!.layoutManager = LinearLayoutManager(vista.context)
        Log.d("onCreateView", "ESTA HECHO")
        llenarLista()
        val amigosAdap = AmigosAdapter(listaAmigos!!)
        recyclerAmigos!!.adapter = amigosAdap
        amigosAdap.notifyDataSetChanged()
        return vista
    }

    fun llenarLista(){
        listaAmigos!!.add(Amigos("MCX", R.drawable.f5c203e9708920b6adc4d789a6c48291,"Jugando a League of Legends"))
        listaAmigos!!.add(Amigos("tizzzte", R.drawable.a598340e466f5c9bd2acb52e21aecfe8,"Jugando a Overwatch 2"))
        listaAmigos!!.add(Amigos("iOmaewa",R.drawable.fc1db171f2d4fde4c7a34a944ed4d8cb,"Jugando a League of Legends"))
        listaAmigos!!.add(Amigos("Mero", R.drawable.d6d113b6d0cfcc4cef4ce2ef6b833c78,""))
        listaAmigos!!.add(Amigos("locoyoloGY",R.drawable.a366d5d2525588f2429d0da90980bacf,"Escuchando Spotify"))
        listaAmigos!!.add(Amigos("fxck teo", R.drawable.a4b4d07386c889f507d0ac9029733f4d,"Jugando a Overwatch 2"))
        listaAmigos!!.add(Amigos("Kritox", R.drawable.bb42d0b100ff6b77a79d185f096494a8,"Jugando a Genshin Impact "))
        listaAmigos!!.add(Amigos("Emeee", R.drawable.a5c5a76f5564401dc5908a9f99d0b8af,"Jugando a Roblox"))
        listaAmigos!!.add(Amigos("Brael", R.drawable.a61bfaad4e49ce8518c13e7182df564,"Jugando a /donate"))
        listaAmigos!!.add(Amigos("Dagom", R.drawable.f45a0a1b90bb9b5abd4671354a4bd99c,""))
        listaAmigos!!.add(Amigos("Sticky2", R.drawable.a6db809fb8b3efa6fc2888cdab4b2cc0,"Jugando a /event"))
        listaAmigos!!.add(Amigos("PRIMVS", R.drawable.a74e1bafa68051b38c41285c1844e18b,"Jugando a Destiny 2"))
        listaAmigos!!.add(Amigos("f0in", R.drawable.a947e784168edde97a4fcbd69c029825,""))
        listaAmigos!!.add(Amigos("Obo", R.drawable.ad31f1f675d46520d2b47b5c9e355834,""))
        listaAmigos!!.add(Amigos("Ter", R.drawable.b164ec856a0621ad217afa29a8409753,"Jugando a Mortal Kombat 11"))
        listaAmigos!!.add(Amigos("Rem02", R.drawable.b840e7cee0aafe96dd649c3897bcbc42,"Jugando a Minecraft"))
        listaAmigos!!.add(Amigos("ROLANXD", R.drawable.ca992ed13f92afd34f057e78fa8e4baa,"Jugando a Rocket League"))
        listaAmigos!!.add(Amigos("Vortex", R.drawable.d363ca46b0a15276449749e4032d1eb3,"Jugando a Dota 2"))
        listaAmigos!!.add(Amigos("Vortex", R.drawable.a2eaf1bc243ddf7f3ff1f43f06164925,""))
        listaAmigos!!.add(Amigos("Vortex", R.drawable.d5912561892bbdaa84e8823b41ab23ad,"Jugando a Destiny 2"))

        Log.d("llenarLista", "ESTA HECHO")
    }

}