package com.example.ptatice

import NotesAdd_main
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ptatice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NotesDatabaseHelper
    private lateinit var noteAdd_main: NotesAdd_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)
        noteAdd_main = NotesAdd_main(db.getAllDetails(), this)

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = noteAdd_main

        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddNote::class.java)
            startActivity(intent)
        }


    }

//    fun onCheckBoxClick(note: Note, isChecked: Boolean) {
//        note.completed = isChecked
//        db.updateNote(note)
//        noteAdd_main.refresh(db.getAllDetails())
//    }

    override fun onResume() {
        super.onResume()
        noteAdd_main.refresh(db.getAllDetails())

    }
}