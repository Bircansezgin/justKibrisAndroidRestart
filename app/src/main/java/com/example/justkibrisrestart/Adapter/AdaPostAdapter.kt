import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softrestart.justkibrisrestart.Class.adaPostClass
import com.softrestart.justkibrisrestart.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class AdaPostAdapter(private val postList: List<adaPostClass>, private val onItemClicked: (adaPostClass) -> Unit) : RecyclerView.Adapter<AdaPostAdapter.AdaPostViewHolder>() {

    inner class AdaPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postUserName: TextView = itemView.findViewById(R.id.postUserName)
        val postMekanAdi: TextView = itemView.findViewById(R.id.postMekanAdi)
        val postImage: ImageView = itemView.findViewById(R.id.postImage)
        val postMekanImage: ImageView = itemView.findViewById(R.id.postMekanImage)
        val postTarih: TextView = itemView.findViewById(R.id.postTarih)
        val postAciklama: TextView = itemView.findViewById(R.id.postAciklama)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaPostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ada_post_posts_cell, parent, false)
        return AdaPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdaPostViewHolder, position: Int) {
        val currentItem = postList[position]
        holder.itemView.setOnClickListener {
            onItemClicked(currentItem)
        }

        holder.postUserName.text = currentItem.userName
        holder.postMekanAdi.text = currentItem.activityPlace
        holder.postAciklama.text = currentItem.activityName

        // Tarihi biçimlendirme işlemi
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(currentItem.postSendingDate)
        holder.postTarih.text = formattedDate

        Picasso.get().load(currentItem.postPhotoURL).into(holder.postImage)
        Picasso.get().load(currentItem.activityPosterImageURL).into(holder.postMekanImage)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}
