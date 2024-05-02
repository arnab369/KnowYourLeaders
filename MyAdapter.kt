package com.example.knowyourleaders


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class MyAdapter(private val leadersList : ArrayList<Leaders>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener


    interface onItemClickListener {
        fun onItemClick(position : Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    fun deleteItem(i : Int) {
        leadersList.removeAt(i)
        notifyDataSetChanged()
    }

    fun addItem(i: Int, leaders: Leaders) {
        leadersList.add(i, leaders)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
            parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = leadersList[position]
        holder.titlePMImage.setImageResource(currentItem.titleImage)
        holder.tvPMHeading.text = currentItem.heading

    }

    override fun getItemCount(): Int {
        return leadersList.size
    }

    fun filter(searchText: String) {
        val filteredList = ArrayList<Leaders>()

        for (item in leadersList) {
            if (item.heading.contains(searchText, ignoreCase = true)) {
                filteredList.add(item)
            }
        }

        leadersList.clear()
        leadersList.addAll(filteredList)
        notifyDataSetChanged()
    }



    class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val titlePMImage : ShapeableImageView = itemView.findViewById(R.id.pm_modi)
        val tvPMHeading : TextView = itemView.findViewById(R.id.tvPMHeading)


        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}