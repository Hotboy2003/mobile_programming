package com.Obynochniy.lab_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_first.view.*


class firstFragment : Fragment() {
    private val myAdapter = PhonesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_first, container, false)

        root.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        root.recyclerView.adapter = myAdapter

        loadData()

        return root
    }

    private fun loadData() {
        val list = PhonesData.phonesArr.toCollection(ArrayList())
        myAdapter.setupPhones(list)
    }
}
