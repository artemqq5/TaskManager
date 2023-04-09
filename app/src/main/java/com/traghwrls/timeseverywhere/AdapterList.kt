package com.traghwrls.timeseverywhere

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.traghwrls.timeseverywhere.databinding.ItemRecyclerBinding

class AdapterList(var myList: List<TimeModel> = emptyList()) :
    RecyclerView.Adapter<AdapterList.ViewAdapterHolder>() {

    inner class ViewAdapterHolder(view: View) :  RecyclerView.ViewHolder(view) {
        private val binding = ItemRecyclerBinding.bind(view)

        fun bind(model: TimeModel) {
            binding.datetime.text = model.datetime
            binding.requestedLocation.text = model.requestedLocation
            binding.timezoneLocation.text = model.timezoneLocation
            binding.timezoneName.text = model.timezoneName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAdapterHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ViewAdapterHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: ViewAdapterHolder, position: Int) {
        holder.bind(myList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeList(newList: List<TimeModel>) {
        myList = newList.reversed()
        notifyDataSetChanged()
    }

}