package com.codercampy.androidproject

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NoteSaver(context: Context) {

    companion object {

        private const val NOTES = "notes"

    }

    private val sharedPref = context.getSharedPreferences(NOTES, Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()
    private val gson = Gson()

    fun saveNote(note: Note) {
        val allNotes = getAllNotes().toMutableList()
        allNotes.add(note)

        //Converting list of objects to string
        val listType = object : TypeToken<List<Note>>() {}.type
        val data = gson.toJson(allNotes, listType)

        editor.putString(NOTES, data)
        editor.commit()
    }

    fun getAllNotes(): List<Note> {
        return try {
            val data = sharedPref.getString(NOTES, "")

            //Converting string to list of objects
            val listType = object : TypeToken<List<Note>>() {}.type
            gson.fromJson(data, listType)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun deleteNote(pos: Int) {
        val allNotes = getAllNotes().toMutableList()
        allNotes.removeAt(pos)

        //Converting list of objects to string
        val listType = object : TypeToken<List<Note>>() {}.type
        val data = gson.toJson(allNotes, listType)

        editor.putString(NOTES, data)
        editor.commit()
    }

}