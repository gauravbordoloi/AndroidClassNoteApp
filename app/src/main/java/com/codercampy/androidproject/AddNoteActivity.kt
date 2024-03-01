package com.codercampy.androidproject

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codercampy.androidproject.databinding.ActivityAddNoteBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AddNoteActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPress()
        }

        binding.btnSave.setOnClickListener {
            addNote()
        }

    }

    private fun onBackPress() {
        val title = binding.inputTitle.editText?.text?.trim()?.toString()
        val body = binding.inputBody.editText?.text?.trim()?.toString()
        if (!title.isNullOrEmpty() || !body.isNullOrEmpty()) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Warning")
                .setMessage("Are you sure you want to go back? Your data will be lost!")
                .setNegativeButton("Stay", null)
                .setPositiveButton("Go Back") { dialog, which ->
                    finish()
                }.show()
        } else {
            finish()
        }
    }

    private fun addNote() {
        val title = binding.inputTitle.editText?.text?.trim()?.toString()
        val body = binding.inputBody.editText?.text?.trim()?.toString()

        if (title.isNullOrEmpty() || body.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter data", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent()
        intent.putExtra("note", Note(title, body))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}