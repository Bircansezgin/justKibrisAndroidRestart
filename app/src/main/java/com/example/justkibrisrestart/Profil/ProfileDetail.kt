package com.softrestart.justkibrisrestart.Profil

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.softrestart.justkibrisrestart.Class.userSingleton

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.softrestart.justkibrisrestart.Adapter.RoundedCornersTransformation
import com.softrestart.justkibrisrestart.Login.LoginVC
import com.softrestart.justkibrisrestart.R
import com.softrestart.justkibrisrestart.databinding.ActivityProfileDetailBinding
import com.squareup.picasso.Picasso


class ProfileDetail : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding?.cikisYapButton?.setOnClickListener{
            showAlertDialog("Çıkış", "Çıkış yapmak istediğinize emin misiniz?")
        }


        binding?.hesabimiSilButton?.setOnClickListener {
            showDeleteConfirmationAlert()
        }
        setupUI_user()
    }

    private fun showAlertDialog(title: String, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("Evet") { dialog, _ ->
                // Kullanıcı 'Evet' dediğinde yapılacak işlemler
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@ProfileDetail, LoginVC::class.java)
                startActivity(intent)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                finish()
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            }
            setNegativeButton("Hayır") { dialog, _ ->
                // Kullanıcı 'Hayır' dediğinde yapılacak işlemler
                dialog.dismiss()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }



    private fun showDeleteConfirmationAlert() {
        val alertController = AlertDialog.Builder(this)
        alertController.setTitle("Hesap Silme")
        alertController.setMessage("Hesabınızı silmek istediğinize emin misiniz?")
        alertController.setPositiveButton("Sil") { _, _ ->
            val intent = Intent(this@ProfileDetail, LoginVC::class.java)
            startActivity(intent)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            finish()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            //confirmDeleteAccount()
        }
        alertController.setNegativeButton("İptal", null)
        alertController.show()
    }

    private fun confirmDeleteAccount() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                deleteAccountFromFirestore()
                Log.d("Auth", "Hesap Başarıyla Silindi")
            } else {
                Log.e("Auth", "Auth Hesap Silme Hatası: ${task.exception?.localizedMessage}")
                promptForReauthentication()
            }
        }
    }

    private fun promptForReauthentication() {
        val reauthenticationAlertBuilder = AlertDialog.Builder(this)
        reauthenticationAlertBuilder.setTitle("Kimlik Doğrulama")
        reauthenticationAlertBuilder.setMessage("Bu İşlemi Doğrulamamız için Tekrar Giriş yapmamız gerekmektedir.")
        reauthenticationAlertBuilder.setPositiveButton("Tamam") { _, _ ->
         //
        }
        reauthenticationAlertBuilder.show()
    }

    private fun deleteAccountFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val userID = userSingleton.userDocumentID // userSingleton üzerinden Firebase kullanıcı ID'sine erişim
        db.collection("userInformation").document(userID)
            .delete()
            .addOnSuccessListener {
                handleAccountDeleted()
                Log.d("Firestore", "Firestore Hesap Başarıyla Silindi")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Firestore Hesap Silme Hatası: ${e.localizedMessage}")
            }
    }

    private fun handleAccountDeleted() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hesap Durumu")
        builder.setMessage("Hesabınız Başarılı bir şekilde silinmiştir. Aramızdan ayrıldığınız için çok üzgünüz. Umarım en kısa zamanda seni görmeyi çok isteriz.")
        builder.setPositiveButton("OK") { _, _ ->
            // login ekranına dön
            goLoginPage()
        }
        builder.show()
    }


    private fun setupUI_user(){
        binding.kullanciAd.text = userSingleton.username
        binding.kullaniciePosta.text = userSingleton.email
        binding.kullaniciTelNo.text = userSingleton.phoneNumber
        binding.kullaniciYas.text = userSingleton.age
        binding.kullaniciCuzdanNumarasi.text = userSingleton.userWalletID

        Picasso.get()
            .load(userSingleton.userImageURL)
            .transform(RoundedCornersTransformation(30f)) // Kenar yarıçapını ayarlayabilirsiniz
            .into(binding.imageView2)
    }

    private fun goLoginPage(){
        val intent = Intent(this@ProfileDetail, LoginVC::class.java)
        startActivity(intent)
        finish()
    }

}
