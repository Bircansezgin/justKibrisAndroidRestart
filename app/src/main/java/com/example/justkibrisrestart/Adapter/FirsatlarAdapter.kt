package com.softrestart.justkibrisrestart.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softrestart.justkibrisrestart.Firsatlar.firsatDetayCell
import com.softrestart.justkibrisrestart.Class.FirsatlarC
import com.softrestart.justkibrisrestart.R
import com.squareup.picasso.Picasso

class firsatlarAdapter(private val firsatList: List<FirsatlarC>) : RecyclerView.Adapter<firsatlarAdapter.FirsatlarViewHolder>() {

    class FirsatlarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firsatBasligi: TextView = itemView.findViewById(R.id.firsatBaslik)
        val firsatAciklamasi: TextView = itemView.findViewById(R.id.firsatAciklama)
        val firsatSonTarih: TextView = itemView.findViewById(R.id.firsatTarih)
        val firsatImage : ImageView = itemView.findViewById(R.id.firsatImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirsatlarViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.firsatlar_cell, parent, false)
        return FirsatlarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FirsatlarViewHolder, position: Int) {
        val currentItem = firsatList[position]

        //holder.mekanIsimText.text = "Başlık : ${currentItem.ClubTitle}"
        //  holder.etkinlikTarih.text = "Fiyat : ${currentItem.ilanFiyat}"
        //  holder.etkinlikSaat.text = "Minimum Kiralama : ${currentItem.ilanKiraMinSuresi}"
        //  holder.ticketFiyat.text = "Oda : ${currentItem.evOdaSayisi}"
        //  Picasso.get().load(currentItem.photoURLArray[0]).into(holder.im)

        holder.firsatBasligi.text = currentItem.firsatBasligi
        holder.firsatAciklamasi.text = currentItem.firsatAciklamasi
        holder.firsatSonTarih.text = currentItem.firsatSonTarih
        Picasso.get().load(currentItem.imageUrl).into(holder.firsatImage)

        holder.itemView.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, firsatDetayCell::class.java)
            intent.putExtra("selectedFirsat", currentItem)
            context.startActivity(intent)
        }



        // Diğer özellikleri de buraya ekleyebilirsiniz.
    }

    override fun getItemCount(): Int {
        return firsatList.size
    }
}

