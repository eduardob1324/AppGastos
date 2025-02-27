package com.ed.app_gastos.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ed.app_gastos.domain.modelo.GastoDto

class GastosAdapter(private val gastos :List<GastoDto>) : RecyclerView.Adapter<GastosViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastosViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: GastosViewHolder, position: Int) {

    }

    override fun getItemCount() = gastos.size
}