package com.softrestart.justkibrisrestart.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.softrestart.justkibrisrestart.Class.TopUser
import com.softrestart.justkibrisrestart.R
import com.squareup.picasso.Picasso

class TopUsersAdapter(private val topUsersList: List<TopUser>, private val onItemClicked: (TopUser) -> Unit) : RecyclerView.Adapter<TopUsersAdapter.TopUsersViewHolder>() {

    class TopUsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userPostProfile: ImageView = itemView.findViewById(R.id.userPostProfile1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopUsersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_top_users_cell,parent,false)
        return TopUsersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TopUsersViewHolder, position: Int) {
        val currentItem = topUsersList[position]
        holder.itemView.setOnClickListener {
            onItemClicked(currentItem)
        }

        Picasso.get().load(currentItem.photoURL).into(holder.userPostProfile)
    }

    override fun getItemCount(): Int {
        return topUsersList.size
    }
}