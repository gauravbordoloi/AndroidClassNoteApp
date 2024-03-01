package com.codercampy.androidproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.codercampy.androidproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {

        private const val ADD_NOTE_RESULT = 1267

    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteSaver: NoteSaver
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        noteSaver = NoteSaver(this)

        adapter = MyAdapter(object : MyAdapterListener {
            override fun onItemDelete(pos: Int) {
                deleteNote(pos)
            }
        })
        binding.recyclerView.adapter = adapter

        binding.btnAdd.setOnClickListener {
            navigateToAddScreen()
        }

        //load all the saved notes
        val savedNotes = noteSaver.getAllNotes()
        if (savedNotes.isEmpty()) {
            binding.animationView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.animationView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            adapter.addNotes(savedNotes)
        }
    }

    private fun deleteNote(pos: Int) {
        noteSaver.deleteNote(pos)
        adapter.deleteNote(pos)

        if (adapter.itemCount == 0) {
            binding.animationView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }

    private fun navigateToAddScreen() {
        startActivityForResult(
            Intent(this, AddNoteActivity::class.java),
            ADD_NOTE_RESULT
        )
    }

    private fun addNote(note: Note) {
        adapter.addNote(note)
        noteSaver.saveNote(note)

        if (adapter.itemCount > 0) {
            binding.animationView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_RESULT && resultCode == Activity.RESULT_OK) {
            val note = data?.getParcelableExtra<Note>("note") ?: return
            addNote(note)
        }
    }

}