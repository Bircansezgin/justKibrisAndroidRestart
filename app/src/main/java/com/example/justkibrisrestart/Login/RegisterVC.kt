package com.example.justkibrisrestart.Login

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.softrestart.justkibrisrestart.Login.LoginVC
import com.softrestart.justkibrisrestart.databinding.ActivityRegisterVcBinding
import java.util.UUID

class RegisterVC : AppCompatActivity() {

    lateinit var binding: ActivityRegisterVcBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private var selectedImageUri: Uri? = null


    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterVcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference.child("userProfileAndroid")

        binding.userImageView.setOnClickListener {
            openGallery()
        }

        binding.registerButton.setOnClickListener {
            // Kullanıcının tüm bilgileri girdiğinden ve fotoğraf seçtiğinden emin olun
            if (selectedImageUri != null && isAllUserInfoEntered()) {
                registerUser(selectedImageUri!!)
            } else {
                Toast.makeText(this, "Lütfen tüm bilgilerinizi girin ve bir resim seçin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            binding.userImageView.setImageURI(selectedImageUri)
        }
    }

    private fun isAllUserInfoEntered(): Boolean {
        return binding.firstNameEditText.text.isNotBlank() &&
                binding.lastNameEditText.text.isNotBlank() &&
                binding.usernameText.text.isNotBlank() &&
                binding.ageText.text.isNotBlank() &&
                binding.emailText.text.isNotBlank() &&
                binding.passwordText.text.isNotBlank() &&
                binding.phoneText.text.isNotBlank()
    }


    private fun registerUser(selectedImageUri: Uri) {

        val name = binding.firstNameEditText.text.toString()
        val surname = binding.lastNameEditText.text.toString()
        val username = binding.usernameText.text.toString()
        val age = binding.ageText.text.toString().toString()
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()
        val phone = binding.phoneText.text.toString()

        val uuid = UUID.randomUUID().toString()
        var customDocumentName = username + uuid
        val rnds = (0..999999).random()
        var userWalletID = username + rnds

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user = hashMapOf(
                        "documentID" to customDocumentName,
                        "name" to name,
                        "surname" to surname,
                        "username" to username,
                        "password" to password,
                        "age" to age,
                        "email" to email,
                        "phoneNumber" to phone,
                        "Kayit_Cihazi" to "Android",
                        "KayitTarihi" to FieldValue.serverTimestamp(),
                        "userActive" to 1,
                        "userWallet" to 30,
                        "photoURL" to "",
                        "userWalletID" to userWalletID,
                        "userWalletQRCodeData" to "userID:${customDocumentName};walletID:${userWalletID}"
                    )

                    firestore.collection("userInformation").document(customDocumentName)
                        .set(user)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(this, "Başarılı, Fotoğraf Yükleniyor!!!", Toast.LENGTH_SHORT).show()

                            val imageRef = storageRef.child(customDocumentName)

                            // Fotoğrafı Storage'a yükle
                            imageRef.putFile(selectedImageUri)
                                .addOnSuccessListener { _ ->
                                    // Yükleme başarılı olursa URL'i al ve Firestore'da güncelle
                                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                                        firestore.collection("userInformation").document(customDocumentName)
                                            .update("photoURL", uri.toString())
                                            .addOnSuccessListener {
                                                Toast.makeText(this, "Başarılı Kayıt Oluşturuldu!", Toast.LENGTH_SHORT).show()
                                                navigateToSuccessPage()
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(this, "Hata Oluştu", Toast.LENGTH_SHORT).show()
                                            }
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Hata Oluştu", Toast.LENGTH_SHORT).show()
                                }

                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Hata Oluştu", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // If registration fails, display a message to the user.
                    // You can handle specific failure cases if needed.
                    Toast.makeText(this, "Kayıt Başarısız", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun navigateToSuccessPage() {
        val intent = Intent(this, LoginVC::class.java)
        startActivity(intent)
        finish()  // Bu, bu aktivitenin geri dönüşü olmayacak şekilde kapatılmasını sağlar
    }
}
