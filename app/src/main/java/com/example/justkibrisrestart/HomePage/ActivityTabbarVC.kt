package com.example.justkibrisrestart.HomePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.justkibrisrestart.R
import com.example.justkibrisrestart.databinding.ActivityTabbarVcBinding

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
