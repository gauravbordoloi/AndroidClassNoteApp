package com.codercampy.androidproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codercampy.androidproject.databinding.ItemNoteBinding

class MyAdapter(
    private val listener: MyAdapterListener
): RecyclerView.Adapter<StudentViewHolder>() {

    private val notes = mutableListOf<Note>()

    fun addNote(note: Note) {
        notes.add(note)
        notifyItemInserted(notes.size - 1)
    }

    fun addNotes(data: List<Note>) {
        var endIndex = data.size - 1
        if (endIndex < 0) {
            endIndex = 0
        }

        notes.addAll(data)
        notifyItemRangeInserted(endIndex, notes.size - 1)
    }

    fun deleteNote(pos: Int) {
        notes.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        //create the view
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(layoutInflater, parent, false)
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        //list count
        return notes.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        //data bind
        holder.binding.tvTitle.text = notes[position].title
        holder.binding.tvBody.text = notes[position].body

        holder.binding.btnDelete.setOnClickListener {
            listener.onItemDelete(position)
        }
    }

}

class StudentViewHolder(
    val binding: ItemNoteBinding
): RecyclerView.ViewHolder(binding.root) {



}

