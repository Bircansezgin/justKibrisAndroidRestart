package com.example.justkibrisrestart

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.justkibrisrestart.HomePage.ActivityTabbarVC
import com.example.justkibrisrestart.databinding.ActivityLoginVcBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginVC : AppCompatActivity() {

    private lateinit var binding : ActivityLoginVcBinding
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginVcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Firebase AUTH Instance
        auth = FirebaseAuth.getInstance()

    }

    // MARK: - Button
    fun GuestButtonClick(view: View){
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val intent = Intent(this, ActivityTabbarVC::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Giriş başarısız
                    showError("Giriş başarısız! Daha Sonra Terkar Deneyiniz")
                }
            }
            .addOnFailureListener(this) { exception ->
                // Giriş işlemi başarısız olduğunda gerçekleştirilecek
                showError("Giriş sırasında hata oluştu: ${exception.message}")
            }
    }


    private fun showError(errorMessage: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Hata")
        alertDialogBuilder.setMessage(errorMessage)
        alertDialogBuilder.setPositiveButton("Tamam") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}