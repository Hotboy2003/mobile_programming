package com.Obynochniy.lab_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhonesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var mPhonesList: ArrayList<PhoneModel> = ArrayList()

    fun setupPhones(phonesList: ArrayList<PhoneModel>)
    {
        mPhonesList.clear()
        mPhonesList.addAll(phonesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.recyclerview_item, parent, false)
        return PhonesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        if (holder is PhonesViewHolder)
        {
            holder.bind(mPhones = mPhonesList[position])
        }
    }

    override fun getItemCount(): Int
    {
        return mPhonesList.count()
    }

    inner class PhonesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(mPhones: PhoneModel) {
            itemView.findViewById<TextView>(R.id.title).text = mPhones.name
            itemView.findViewById<TextView>(R.id.price).text = "Launch price: " + mPhones.price
            itemView.findViewById<TextView>(R.id.date).text = "Launch date: " + mPhones.date
            itemView.findViewById<TextView>(R.id.score).text = "Camera score: " + mPhones.score
        }
    }
}

