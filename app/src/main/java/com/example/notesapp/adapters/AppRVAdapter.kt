package com.example.notesapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ListItemBinding
import com.example.notesapp.model.Notes

class AppRVAdapter(val ls : List<Notes>): RecyclerView.Adapter<AppRVAdapter.AppViewHolder>() {
    class AppViewHolder(val b:ListItemBinding) :RecyclerView.ViewHolder(b.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return AppViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val note = ls.get(position)
        holder.b.apply {
            idNumber.text = (position+1).toString()
            idDate.text = note.date
            idHeading.text = note.heading
            idTopic.text = note.body
            idType.text = note.type

            root.setOnClickListener{
                onItemClickListener?.let { it(note) }
            }
        }
    }

    private var onItemClickListener:((Notes)->Unit)? = null

    fun setOnItemClickListener(listener:(Notes) -> Unit){
        onItemClickListener = listener
    }
    override fun getItemCount(): Int {
        return ls.size
    }
}