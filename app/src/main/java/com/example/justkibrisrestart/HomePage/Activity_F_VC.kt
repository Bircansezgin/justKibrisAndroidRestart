package com.softrestart.justkibrisrestart.HomePage

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softrestart.justkibrisrestart.Adapter.ActivityAdapter
import com.softrestart.justkibrisrestart.Class.ActivityClass
import com.softrestart.justkibrisrestart.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
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

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_activity_v_c, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        setupFetchActivity(view)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disableTouchFor3Seconds()

        // onViewCreated içinde view parametresini kullanarak setupFetchActivity fonksiyonunu çağır
        // Verileri daha önce çekmediyse ve çekecekse
        if (!dataAlreadyFetched) {
            setupFetchActivity(view)
            dataAlreadyFetched = true
        }
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




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ActivityVC.
         */
        // TODO: Rename and change types and number of parameters
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