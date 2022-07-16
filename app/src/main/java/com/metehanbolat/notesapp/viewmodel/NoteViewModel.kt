package com.metehanbolat.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metehanbolat.notesapp.data.Note
import com.metehanbolat.notesapp.repositories.NotesRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val notesRepo: NotesRepo
): ViewModel() {

    private val _searchNotes = MutableStateFlow<List<Note>>(emptyList())
    val searchNotes: StateFlow<List<Note>> = _searchNotes

    val notes = notesRepo.getNotes()
    fun upsertNote(note: Note) = viewModelScope.launch { notesRepo.upsertNote(note) }
    fun deleteNote(note: Note) = viewModelScope.launch { notesRepo.deleteNote(note) }
    fun deleteAllNotes() = viewModelScope.launch { notesRepo.deleteAllNotes() }
    fun searchNotes(searchQuery: String) = viewModelScope.launch {
        notesRepo.searchNotes(searchQuery).collect { notesList -> _searchNotes.emit(notesList) }
    }

}