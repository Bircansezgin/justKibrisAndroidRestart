package com.example.justkibrisrestart.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softrestart.justkibrisrestart.Adapter.SearchAdapter
import com.softrestart.justkibrisrestart.Class.SearchItem
import com.softrestart.justkibrisrestart.R

class Search_F_VC : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search__f__v_c, container, false)

        // Initialize RecyclerView and adapter
        recyclerView = view.findViewById(R.id.searchItemReceycler)
        searchAdapter = SearchAdapter(getSampleSearchItems())

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = searchAdapter

        // Return the initialized view, not a new instance
        return view
    }

    private fun getSampleSearchItems(): List<SearchItem> {
        // Create sample search items
        val searchItems = mutableListOf<SearchItem>()
        searchItems.add(SearchItem("Prime Events", "Event"))
        searchItems.add(SearchItem("Bar", "Entertainment"))
        searchItems.add(SearchItem("Konser", "Music"))
        searchItems.add(SearchItem("Restoran", "Food"))
        searchItems.add(SearchItem("Party", "Event"))
        searchItems.add(SearchItem("Yurt", "Yurt"))
        searchItems.add(SearchItem("Meyhane", "Meyhane"))
        searchItems.add(SearchItem("Cafe", "Cafe"))
        searchItems.add(SearchItem("Esnaf", "Esnaf"))
        searchItems.add(SearchItem("Mağusa", "Mağusa"))
        searchItems.add(SearchItem("Lefkoşa", "Lefkoşa"))
        searchItems.add(SearchItem("Girne", "Girne"))
        searchItems.add(SearchItem("İskele", "İskele"))
        searchItems.add(SearchItem("Lefke", "Lefke"))
        searchItems.add(SearchItem("Güzel Y.", "G.Yurt"))



        return searchItems
    }
}
