package com.example.modul3xml

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.modul3xml.databinding.ItemLegoBinding

class LegoAdapter(private val listData: List<Lego>) : RecyclerView.Adapter<LegoAdapter.LegoViewHolder>() {

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
            val link = Uri.parse(item.webUrl)
            val intentWeb = Intent(Intent.ACTION_VIEW, link)
            holder.itemView.context.startActivity(intentWeb)
        }

        holder.binding.btnDetail.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("data_lego", item)
            holder.itemView.findNavController().navigate(R.id.action_list_to_detail, bundle)
        }
    }

    override fun getItemCount(): Int = listData.size
}