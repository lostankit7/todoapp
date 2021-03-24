package com.android.noteapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.noteapp.databinding.ItemListBinding
import com.android.noteapp.model.Note

class RvListAdapter(private val context: Context,private val listener: IDeleteNote) : RecyclerView.Adapter<RvListAdapter.ViewHolder>() {

    private var list = mutableListOf<Note>()

    fun updateList(l: List<Note>){
        list.clear()
        list = l as MutableList<Note>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvList.text = list[position].title
        holder.binding.tvList.setOnClickListener {
            listener.onItemClick(list[holder.adapterPosition])
        }
        holder.binding.ivAction.setOnClickListener{
            listener.delete(list[holder.adapterPosition],holder.binding.ivAction)
        }
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
    }

}

interface IDeleteNote{
    fun onItemClick(note: Note)
    fun delete(note: Note,iv: ImageView)
}