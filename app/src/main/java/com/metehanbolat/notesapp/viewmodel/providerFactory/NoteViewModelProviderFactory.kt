package com.metehanbolat.notesapp.viewmodel.providerFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.metehanbolat.notesapp.repositories.NotesRepo

class NoteViewModelProviderFactory(
    private val notesRepo: NotesRepo
): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(NotesRepo::class.java).newInstance(notesRepo)
    }
}