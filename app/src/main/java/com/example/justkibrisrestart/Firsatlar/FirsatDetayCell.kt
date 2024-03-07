package com.softrestart.justkibrisrestart.Firsatlar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.softrestart.justkibrisrestart.Class.FirsatlarC
import com.softrestart.justkibrisrestart.databinding.ActivityFirsatDetayCellBinding
import com.squareup.picasso.Picasso


class firsatDetayCell : AppCompatActivity() {

    private lateinit var binding: ActivityFirsatDetayCellBinding

    var commonFirsat : FirsatlarC? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirsatDetayCellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        commonFirsat = intent.getSerializableExtra("selectedFirsat") as? FirsatlarC
        setupUI(firsatlar = commonFirsat)
    }


    fun setupUI(firsatlar: FirsatlarC?){
        firsatlar?.let{

            binding.firsatAciklama12.text = firsatlar.firsatAciklamasi
            binding.firsatDetaytextView5.text = firsatlar.firsatBasligi
            binding.firsatEskFYatText.text = firsatlar.firsatEskiTutar.toString()
            binding.firsatYenFYatText.text = firsatlar.firsatYeniTutar.toString()
            binding.firsatDetaytextView7.text = firsatlar.firsatSonTarih

            if (commonFirsat?.imageUrl != null) { // imageURL'nin boş olmadığından emin olun
                val imageView = binding.firsatDetayImageView
                Picasso.get()
                    .load(commonFirsat!!.imageUrl)
                    .into(imageView)
            } else {
                // imageURL boşsa yapılacak işlemleri buraya ekleyin (örneğin, varsayılan bir resim yüklemek)
            }
        }
    }

    fun satinAl(view: View) {
    }
}