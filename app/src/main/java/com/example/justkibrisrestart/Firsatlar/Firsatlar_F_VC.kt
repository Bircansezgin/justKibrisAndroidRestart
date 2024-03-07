package com.softrestart.justkibrisrestart.Firsatlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.softrestart.justkibrisrestart.Adapter.firsatlarAdapter
import com.softrestart.justkibrisrestart.Class.FirsatlarC
import com.softrestart.justkibrisrestart.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Firsatlar_F_VC.newInstance] factory method to
 * create an instance of this fragment.
 */
class Firsatlar_F_VC : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_firsatlar__f__v_c, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firsatlarActivities()
    }

    private fun firsatlarActivities() {
        val db = Firebase.firestore

        db.collection("firsatlar")
            .whereEqualTo("isActive", 1)
            .get()
            .addOnSuccessListener { result ->
                val firsatlarList = mutableListOf<FirsatlarC>()
                for (document in result) {
                    val firsatlarFB = document.toObject(FirsatlarC::class.java)
                    firsatlarList.add(firsatlarFB)
                }
                updateRecycler(firsatlarList)
            }
    }


    fun updateRecycler(gelenFirsatlar: List<FirsatlarC>) {
        val firsatAdapter = firsatlarAdapter(gelenFirsatlar)

        view?.findViewById<RecyclerView>(R.id.firsatRecycler)?.adapter = firsatAdapter
        view?.findViewById<RecyclerView>(R.id.firsatRecycler)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = firsatAdapter
        }
    }

}