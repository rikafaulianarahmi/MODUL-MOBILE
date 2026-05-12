package com.example.modul3xml

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modul3xml.databinding.ItemLegoBinding

class LegoAdapter(
    private val listData: List<Lego>,
    private val onDetailClick: (Lego) -> Unit,
    private val onWebClick: (Lego) -> Unit
) : RecyclerView.Adapter<LegoAdapter.LegoViewHolder>() {

    class LegoViewHolder(val binding: ItemLegoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegoViewHolder {
        val view = ItemLegoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LegoViewHolder(view)
    }

    override fun onBindViewHolder(holder: LegoViewHolder, position: Int) {
        val item = listData[position]

        holder.binding.tvTitle.text = item.title
        holder.binding.tvYear.text = item.year
        holder.binding.tvTheme.text = item.theme
        holder.binding.tvDescription.text = item.description
        holder.binding.ivLego.setImageResource(item.imageRes)

        holder.binding.btnWeb.setOnClickListener {
            onWebClick(item)
        }

        holder.binding.btnDetail.setOnClickListener {
            onDetailClick(item)
        }
    }

    override fun getItemCount(): Int = listData.size
}