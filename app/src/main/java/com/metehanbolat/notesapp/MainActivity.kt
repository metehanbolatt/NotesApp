package com.metehanbolat.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.metehanbolat.notesapp.data.db.NoteDatabase
import com.metehanbolat.notesapp.repositories.NotesRepo
import com.metehanbolat.notesapp.viewmodel.NoteViewModel
import com.metehanbolat.notesapp.viewmodel.providerFactory.NoteViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    val viewModel: NoteViewModel by lazy {
        val database = NoteDatabase.getDatabaseInstance(this)
        val notesRepo = NotesRepo(database)
        val notesProviderFactory = NoteViewModelProviderFactory(notesRepo)
        ViewModelProvider(this, notesProviderFactory)[NoteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}