package com.softrestart.justkibrisrestart.HomePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.softrestart.justkibrisrestart.HomePage.Activity_F_VC
import com.softrestart.justkibrisrestart.Profil.Profile_F_VC
import com.softrestart.justkibrisrestart.R
import com.softrestart.justkibrisrestart.databinding.ActivityTabbarVcBinding

class ActivityTabbarVC : AppCompatActivity() {

    private lateinit var binding : ActivityTabbarVcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabbarVcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(Activity_F_VC())


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.etkinlikler -> replaceFragment(Activity_F_VC())
                R.id.profile -> replaceFragment(Profile_F_VC())


                else -> {
                }
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmnetManager = supportFragmentManager
        val fragmentTransaction = fragmnetManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}
