package com.example.justkibrisrestart.adaPost

import AdaPostAdapter
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.softrestart.justkibrisrestart.Adapter.TopUsersAdapter
import com.softrestart.justkibrisrestart.Class.TopUser
import com.softrestart.justkibrisrestart.Class.adaPostClass
import com.softrestart.justkibrisrestart.R


class AdaPost_F_VC : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ada_post__f__v_c, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchAllPosts()
        fetchTopUsers()
    }


    private fun fetchAllPosts() {
        val db = FirebaseFirestore.getInstance()
        val postList = mutableListOf<adaPostClass>()

        db.collection("Posts").whereEqualTo("isActivePost",2,)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val post = document.toObject(adaPostClass::class.java)
                    postList.add(post)
                }
                displayPOST(postList) // Tüm gönderiler alındıktan sonra displayPOST fonksiyonunu çağır
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    fun fetchTopUsers() {
        val db = FirebaseFirestore.getInstance()
        db.collection("userInformation")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val topUsers = mutableListOf<TopUser>()

                for (document in querySnapshot.documents) {
                    val data = document.data
                    val photoURL = data?.get("photoURL") as? String
                    val walletAmount = data?.get("userWallet") as? Double

                    Log.d("USERS", "KAC : ${photoURL}")
                    val user = TopUser(document.id, photoURL, walletAmount)
                    topUsers.add(user)
                }

                topUsers.sortByDescending { it.walletAmount }

                // Veriler alındıktan sonra ekranda göstermek için displayUsers fonksiyonunu çağır
                displayUsers(topUsers)

                Log.d("fetchTopUsers", "Başarıyla kullanıcı bilgileri alındı: $topUsers")
            }
            .addOnFailureListener { exception ->
                Log.e("fetchTopUsers", "Kullanıcı bilgileri çekilirken hata oluştu: $exception")
            }
    }


    private fun displayUsers(topUsers: List<TopUser>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.topUserRecycler)
        val adapter = TopUsersAdapter(topUsers) { clickedUser ->
            // Burada, RecyclerView'da tıklanan bir kullanıcıyı işleyebilirsiniz
            Toast.makeText(requireContext(), "Clicked on ${clickedUser.userID}", Toast.LENGTH_SHORT).show()
        }

        // Yatay yönde düzen oluşturun
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = layoutManager
        adapter.notifyDataSetChanged()
    }

    private fun displayPOST(adapost: List<adaPostClass>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.postRecycler)
        val adapter = AdaPostAdapter(adapost) { clickedUser ->

        }

        // Yatay yönde düzen oluşturun
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = layoutManager
        adapter.notifyDataSetChanged()
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            AdaPost_F_VC().apply {
                arguments = Bundle().apply {

                }
            }
    }
}