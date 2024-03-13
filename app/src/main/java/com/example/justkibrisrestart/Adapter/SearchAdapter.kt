package com.softrestart.justkibrisrestart.Adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softrestart.justkibrisrestart.Class.SearchItem
import com.softrestart.justkibrisrestart.R

// SearchAdapter.kt
class SearchAdapter(private val searchItems: List<SearchItem>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTextView: TextView = itemView.findViewById(R.id.searchName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        // Inflate the layout for each search item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item_receycler_cell, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        // Bind data to the views in each search item
        val searchItem = searchItems[position]
       holder.nameTextView.text = searchItem.searchName

        holder.itemView.setOnClickListener {
            showAlertDialog(holder.itemView.context, "Seçilen Kategori", "'${searchItem.searchName}' Yakında Eklenecektir")
        }
    }

    override fun getItemCount(): Int {
        return searchItems.size
    }

    fun showAlertDialog(context: Context, title: String, message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
