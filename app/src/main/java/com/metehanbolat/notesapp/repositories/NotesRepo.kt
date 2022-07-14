package com.metehanbolat.notesapp.repositories

import com.metehanbolat.notesapp.data.Note
import com.metehanbolat.notesapp.data.db.NoteDatabase

class NotesRepo(
    notesDatabase: NoteDatabase
) {
    val notesDao = notesDatabase.noteDao

    suspend fun upsertNote(note: Note) = notesDao.upsertNote(note)
    suspend fun deleteNote(note: Note) = notesDao.deleteNote(note)
    fun getNotes() = notesDao.selectNotes()
    fun searchNotes(searchQuery: String) = notesDao.searchInNotesTitle(searchQuery)
    suspend fun deleteAllNotes() = notesDao.deleteAllNotes()
}