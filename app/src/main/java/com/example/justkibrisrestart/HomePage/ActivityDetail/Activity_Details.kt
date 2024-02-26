package com.example.justkibrisrestart.HomePage.ActivityDetail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.justkibrisrestart.Adapter.RoundedCornersTransformation
import com.example.justkibrisrestart.Class.ActivityClass
import com.example.justkibrisrestart.R
import com.example.justkibrisrestart.databinding.ActivityDetailsBinding
import com.example.justkibrisrestart.databinding.ActivityTabbarVcBinding
import com.squareup.picasso.Picasso

class Activity_Details : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsBinding

    var commonActivity: ActivityClass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        commonActivity = intent.getSerializableExtra("activityDetails") as? ActivityClass
        setupUI()
    }




    private fun setupUI() {
        // Assuming these are EditText fields, replace with your actual UI elements
        binding.textViewEventNameValue.text = commonActivity?.mekanName ?: ""
        binding.activityNameTextF.setText(commonActivity?.activityName ?: "")
        binding.activityDateTextF.setText(commonActivity?.activityDate ?: "")
        binding.activityPriceTextF.setText(commonActivity?.activityPrice ?: "")
        binding.activityDescriptionTextF.text = "    ${commonActivity?.activityDescription}"
        binding.phoneTextF.setText(commonActivity?.activityPhoneNumber ?: "")

        Picasso.get()
            .load(commonActivity?.photoURL)
            .transform(RoundedCornersTransformation(30f)) // Kenar yarıçapını ayarlayabilirsiniz
            .into(binding.imageViewHeader)

    }



    fun callButton(view: View) {
        val phoneEditText: EditText = findViewById(R.id.phoneTextF)

        val phoneNumber = phoneEditText.text.toString().trim()

        if (phoneNumber.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            startActivity(intent)
        } else {
            // Handle the case where the phone number is empty
            // You may show a message to the user or perform other actions
        }
    }

}
