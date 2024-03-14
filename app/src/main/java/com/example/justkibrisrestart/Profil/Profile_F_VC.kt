package com.softrestart.justkibrisrestart.Profil

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.softrestart.justkibrisrestart.Adapter.RoundedCornersTransformation
import com.softrestart.justkibrisrestart.Class.userSingleton
import com.softrestart.justkibrisrestart.R
import com.softrestart.justkibrisrestart.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match

class Profile_F_VC : Fragment() {

    private lateinit var binding: FragmentProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI_userProfile()

        // "Benim Hesabım" butonunu bulma ve tıklama işlevselliği ekleme
        binding.benimProfil.setOnClickListener {
            val intent = Intent(activity, ProfileDetail::class.java)
            startActivity(intent)
        }

        binding.wpGo.setOnClickListener {
            Log.e("person", "CALISTI")

            val phoneNumber = "+905488664788"
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    private fun setupUI_userProfile() {
        binding.namePersonLabel.text = userSingleton.name + " " + userSingleton.surname
        binding.emailPersonLabel.text = userSingleton.email

        Picasso.get()
            .load(userSingleton.userImageURL) // Görüntüyü ortalamak için
            .transform(RoundedCornersTransformation(30f)) // Kenar yarıçapını ayarlayabilirsiniz
            .into(binding.userImageView)

    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            Profile_F_VC().apply {
                arguments = Bundle().apply {

                }
            }
    }
}