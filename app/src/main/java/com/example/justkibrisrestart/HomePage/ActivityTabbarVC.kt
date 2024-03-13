package com.softrestart.justkibrisrestart.HomePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.justkibrisrestart.adaPost.AdaPost_F_VC
import com.example.justkibrisrestart.search.Search_F_VC
import com.softrestart.justkibrisrestart.Firsatlar.Firsatlar_F_VC
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.softrestart.justkibrisrestart.HomePage.Activity_F_VC
import com.softrestart.justkibrisrestart.Profil.Profile_F_VC
import com.softrestart.justkibrisrestart.R
import com.softrestart.justkibrisrestart.databinding.ActivityTabbarVcBinding

class ActivityTabbarVC : AppCompatActivity() {

    private lateinit var binding: ActivityTabbarVcBinding
    private var currentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabbarVcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // İlk fragment'ı yükle
        replaceFragment(Activity_F_VC::class.java.name, Activity_F_VC())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.etkinlikler -> replaceFragment(Activity_F_VC::class.java.name, Activity_F_VC())
                R.id.search -> replaceFragment(Search_F_VC::class.java.name, Search_F_VC())
                R.id.adaPost -> replaceFragment(AdaPost_F_VC::class.java.name, AdaPost_F_VC())
                R.id.firsatlar -> replaceFragment(Firsatlar_F_VC::class.java.name, Firsatlar_F_VC())
                R.id.profile -> replaceFragment(Profile_F_VC::class.java.name, Profile_F_VC())
            }
            true
        }
    }


    // TEKRAR TEKRAR BASILINCA YUKLENME DUZELTILDI

    private fun replaceFragment(tag: String, fragment: Fragment) {
        val fragmentManager = supportFragmentManager

        // Eğer hedef fragment zaten yüklenmişse işlem yapma
        if (currentFragmentTag == tag) {
            return
        }

        // Fragmentı değiştir
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment, tag)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        // Mevcut fragment tag'ını güncelle
        currentFragmentTag = tag
    }
}
