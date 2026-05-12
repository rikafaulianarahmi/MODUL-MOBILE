package com.example.modul3xml

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modul3xml.databinding.ItemHighlightBinding

class HighlightAdapter(private val list: List<Lego>) : RecyclerView.Adapter<HighlightAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemHighlightBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHighlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ivHighlight.setImageResource(list[position].imageRes)
    }

    override fun getItemCount(): Int = list.size
}