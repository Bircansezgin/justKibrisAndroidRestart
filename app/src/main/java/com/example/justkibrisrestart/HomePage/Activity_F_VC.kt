package com.softrestart.justkibrisrestart.HomePage

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softrestart.justkibrisrestart.Adapter.ActivityAdapter
import com.softrestart.justkibrisrestart.Class.ActivityClass
import com.softrestart.justkibrisrestart.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.softrestart.justkibrisrestart.Class.userSingleton
import java.util.logging.Handler

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class Activity_F_VC : Fragment() {

    private var dataAlreadyFetched = false
    private lateinit var view: View
    private lateinit var progressBar: ProgressBar

    val db = Firebase.firestore
    val sendBar = mutableListOf<ActivityClass>()
    val sendNight = mutableListOf<ActivityClass>()
    val sendKonser = mutableListOf<ActivityClass>()
    val sendCafe = mutableListOf<ActivityClass>()
    val sendMeyhane = mutableListOf<ActivityClass>()
    val sendParti = mutableListOf<ActivityClass>()
    val sendEsnaf = mutableListOf<ActivityClass>()
    val sendRestoran = mutableListOf<ActivityClass>()


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        getUserData()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_activity_v_c, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        setupFetchActivity(view)

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // onViewCreated içinde view parametresini kullanarak setupFetchActivity fonksiyonunu çağır

            disableTouchFor3Seconds()
            setupFetchActivity(view)
            dataAlreadyFetched = true

    }





    fun setupFetchActivity(view: View) {



        val nightAdapter = ActivityAdapter(sendNight)
        val barAdapter = ActivityAdapter(sendBar)
        val konserAdapter = ActivityAdapter(sendKonser)
        val cafeAdapter = ActivityAdapter(sendCafe)
        val meyhaneAdapter = ActivityAdapter(sendMeyhane)
        val partiAdapter = ActivityAdapter(sendParti)
        val esnafAdapter = ActivityAdapter(sendEsnaf)
        val restoranAdapter = ActivityAdapter(sendRestoran)

        // RecyclerView'lara adapter'ları ata
        view.findViewById<RecyclerView>(R.id.nigthClubRecycler).adapter = nightAdapter
        view.findViewById<RecyclerView>(R.id.BarRecycler).adapter = barAdapter
        view.findViewById<RecyclerView>(R.id.KonserRecycler).adapter = konserAdapter
        view.findViewById<RecyclerView>(R.id.CafeRecycler).adapter = cafeAdapter
        view.findViewById<RecyclerView>(R.id.MeyhaneRecycler).adapter = meyhaneAdapter
        view.findViewById<RecyclerView>(R.id.PartyRecycler).adapter = partiAdapter
        view.findViewById<RecyclerView>(R.id.RestoranRecycler).adapter = restoranAdapter
        view.findViewById<RecyclerView>(R.id.EsnafRecycler).adapter = esnafAdapter

        sendBar.clear()
        sendNight.clear()
        sendKonser.clear()
        sendCafe.clear()
        sendMeyhane.clear()
        sendParti.clear()
        sendEsnaf.clear()
        sendRestoran.clear()


        // Firestore veritabanı referansı
        val db = Firebase.firestore

// Veritabanından tüm etkinlikleri çekme
        db.collection("etkinlikler").whereEqualTo("isActive", 1)
            .get()
            .addOnSuccessListener { result ->

                sendBar.clear()
                sendNight.clear()
                sendKonser.clear()
                sendCafe.clear()
                sendMeyhane.clear()
                sendParti.clear()
                sendEsnaf.clear()
                sendRestoran.clear()

                for (document in result) {

                    // Firestore'dan çekilen belgenin ActivityClass modeline dönüştürülmesi
                    val activity = document.toObject(ActivityClass::class.java)

                    // Kategoriye göre ayırma işlemi
                    when (activity.activityCategory) {
                        "Bar" -> sendBar.add(activity)
                        "Night" -> sendNight.add(activity)
                        "Konser" -> sendKonser.add(activity)
                        "Cafe" -> sendCafe.add(activity)
                        "Meyhane" -> sendMeyhane.add(activity)
                        "Party" -> sendParti.add(activity)
                        "Night Club" -> sendNight.add(activity)
                        "Esnaf-Gündelik" -> sendEsnaf.add(activity)
                        "Restoran" -> sendRestoran.add(activity)
                    }

                    nightAdapter.notifyDataSetChanged()
                    barAdapter.notifyDataSetChanged()
                    konserAdapter.notifyDataSetChanged()
                    cafeAdapter.notifyDataSetChanged()
                    meyhaneAdapter.notifyDataSetChanged()
                    partiAdapter.notifyDataSetChanged()
                    esnafAdapter.notifyDataSetChanged()
                    restoranAdapter.notifyDataSetChanged()

                }

                updateRecyclerViews()
            }
            .addOnFailureListener { exception ->
                // Hata durumunda buraya düşer
                Log.w(TAG, "Error getting documents.", exception)
            }



        view.findViewById<RecyclerView>(R.id.nigthClubRecycler).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = nightAdapter
        }

        view.findViewById<RecyclerView>(R.id.BarRecycler).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = barAdapter
        }

        view.findViewById<RecyclerView>(R.id.KonserRecycler).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = konserAdapter
        }

        view.findViewById<RecyclerView>(R.id.CafeRecycler).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = cafeAdapter
        }

        view.findViewById<RecyclerView>(R.id.MeyhaneRecycler).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = meyhaneAdapter
        }

        view.findViewById<RecyclerView>(R.id.PartyRecycler).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = partiAdapter
        }

        view.findViewById<RecyclerView>(R.id.EsnafRecycler)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = esnafAdapter
        }

        view.findViewById<RecyclerView>(R.id.RestoranRecycler).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = restoranAdapter
        }



    }

    fun updateRecyclerViews() {


        // Adapter'ları oluşturun ve RecyclerView'lara atayın
        val barAdapter = ActivityAdapter(sendBar)
        val nightAdapter = ActivityAdapter(sendNight)
        val konserAdapter = ActivityAdapter(sendKonser)
        val cafeAdapter = ActivityAdapter(sendCafe)
        val meyhaneAdapter = ActivityAdapter(sendMeyhane)
        val partiAdapter = ActivityAdapter(sendParti)
        val esnafAdapter = ActivityAdapter(sendEsnaf)
        val restoranAdaptor = ActivityAdapter(sendRestoran)

        // RecyclerView'lara adapter'ları atayın
        view?.findViewById<RecyclerView>(R.id.nigthClubRecycler)?.adapter = nightAdapter
        view?.findViewById<RecyclerView>(R.id.BarRecycler)?.adapter = barAdapter
        view?.findViewById<RecyclerView>(R.id.KonserRecycler)?.adapter = konserAdapter
        view?.findViewById<RecyclerView>(R.id.CafeRecycler)?.adapter = cafeAdapter
        view?.findViewById<RecyclerView>(R.id.MeyhaneRecycler)?.adapter = meyhaneAdapter
        view?.findViewById<RecyclerView>(R.id.PartyRecycler)?.adapter = partiAdapter
        view?.findViewById<RecyclerView>(R.id.EsnafRecycler)?.adapter = esnafAdapter
        view?.findViewById<RecyclerView>(R.id.RestoranRecycler)?.adapter = restoranAdaptor

    }



    private fun disableTouchFor3Seconds() {
        // ProgressBar'ı görünür yap
        progressBar.visibility = View.VISIBLE

        // Ekranı dokunmaya kapat
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )

        // 3 saniye sonra ProgressBar'ı gizle ve dokunmayı etkinleştir
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = View.GONE
            // Kullanıcı dokunmasını etkinleştir
            enableTouch()
        }, 1500)
    }

    private fun enableTouch() {
        // Ekranı dokunmaya aç
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }


    fun getUserData() {
        Log.e("person", "GET USER OKEY")

        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            println("Kullanıcı oturumu açık değil.")
            return
        }
        Log.e("person", "getUserData running")
        val firebaseDB = FirebaseFirestore.getInstance()
        firebaseDB.collection("userInformation")
            .whereEqualTo("email", currentUserEmail)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    println("Veri alınırken hata oluştu: ${error.localizedMessage}")
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    Log.e("person", "snapshot running")
                    for (document in snapshot.documents) {
                        document.data?.let { updateUserInfo(it) }
                    }
                } else {
                    println("Kullanıcı bilgileri bulunamadı.")
                }
            }
    }

    private fun updateUserInfo(data: Map<String, Any>) {
        Log.e("person", "updateUserInfo OKEY")

        Log.e("person", "updateUserInfo running")
        userSingleton.email = data["email"] as? String ?: ""
        userSingleton.name = data["name"] as? String ?: ""
        userSingleton.surname = data["surname"] as? String ?: ""
        userSingleton.username = data["username"] as? String ?: ""
        userSingleton.age = data["age"] as? String ?: ""
        userSingleton.userImageURL = data["photoURL"] as? String ?: ""
        userSingleton.phoneNumber = data["phoneNumber"] as? String ?: ""
        userSingleton.userID = data["userID"] as? String ?: ""
        userSingleton.userDocumentID = data["documentID"] as? String ?: ""
        userSingleton.userWalletID = data["userWalletID"] as? String ?: ""
        userSingleton.userWalletAmount = (data["userWallet"] as? Long ?: 0).toInt()
        userSingleton.userWalleQRCode = data["userWalletQRCodeData"] as? String ?: ""
        userSingleton.userActive = (data["userActive"] as? Long ?: 0).toInt()

        Log.e("person", "userID ${userSingleton.userID}")
        Log.e("person", "username ${userSingleton.username}")

        if (userSingleton.userActive == 0){
            showAlertDialog(requireContext(), "Hesap Durumu", "Hesabiniz banlandi!")
        }



        val username = userSingleton.username

        val welcomeMessage = if (!username.isNullOrEmpty()) {
            "Hoş Geldiniz, $username"
        } else {
            "Hoş Geldiniz kk"
        }
        view.findViewById<TextView>(R.id.welcomeLabel).text = welcomeMessage
    }

    fun showAlertDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, which ->
            // OK butonuna tıklandığında yapılacak işlemler
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Activity_F_VC().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}