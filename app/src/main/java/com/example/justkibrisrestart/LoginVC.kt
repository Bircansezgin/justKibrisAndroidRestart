package com.softrestart.justkibrisrestart

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.softrestart.justkibrisrestart.HomePage.ActivityTabbarVC
import com.softrestart.justkibrisrestart.databinding.ActivityLoginVcBinding
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
    fun loginButton(view: View) {
        val email = binding.epostaText.text.toString()
        val password = binding.passwordText.text.toString()

        // E-posta ve şifre alanlarının boş olup olmadığını kontrol et
        if (email.isEmpty() || password.isEmpty()) {
            showError("E-posta ve şifre alanlarını doldurun.")
            return
        }

        // Firebase Authentication kullanarak giriş yap
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LoginVC, ActivityTabbarVC::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Giriş başarısız ise kullanıcıya hata mesajını göster
                    showError("Giriş başarısız. E-posta veya şifrenizi kontrol edin.")
                }
            }
    }


    fun registerButtonClick(view: View){
        val intent = Intent(this@LoginVC, RegisterVC::class.java)
        startActivity(intent)
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