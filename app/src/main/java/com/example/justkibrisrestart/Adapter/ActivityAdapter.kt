package com.softrestart.justkibrisrestart.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softrestart.justkibrisrestart.Class.ActivityClass
import com.softrestart.justkibrisrestart.HomePage.ActivityDetail.Activity_Details
import com.softrestart.justkibrisrestart.R
import com.squareup.picasso.Picasso

class ActivityAdapter(private val activityList: List<ActivityClass>) :
    RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    // Inner ViewHolder sınıfı
    class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val activityPlace: TextView = itemView.findViewById(R.id.MekanName)
        val activityPrice: TextView = itemView.findViewById(R.id.activityPrice)
        val activityDate : TextView = itemView.findViewById(R.id.activityDate)
        val activityName : TextView = itemView.findViewById(R.id.ActivitiyName)
        var image : ImageView = itemView.findViewById(R.id.imageView)
        // Diğer TextView'leri buraya ekleyebilirsiniz.
    }

    // onCreateViewHolder, her bir öğe oluşturulduğunda çağrılır
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_recycler_cell, parent, false)
        return ActivityViewHolder(view)
    }

    // onBindViewHolder, her bir öğe görünümüne verileri bağlamak için çağrılır
    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val currentItem = activityList[position]

        // TextView'lere veriyi atayın
        holder.activityPlace.text = currentItem.mekanName
        holder.activityName.text = currentItem.activityName
        holder.activityPrice.text = "Tutar : ${currentItem.activityPrice}"
        holder.activityDate.text = currentItem.activityDate

        Picasso.get()
            .load(currentItem.photoURL)
            .transform(RoundedCornersTransformation(30f)) // Kenar yarıçapını ayarlayabilirsiniz
            .into(holder.image)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, Activity_Details::class.java)
            intent.putExtra("activityDetails", currentItem)
            context.startActivity(intent)
        }

        // Diğer TextView'leri buraya ekleyebilirsiniz.
    }

    // getItemCount, veri kümesinin boyutunu döndürür
    override fun getItemCount(): Int {
        return activityList.size
    }



}

