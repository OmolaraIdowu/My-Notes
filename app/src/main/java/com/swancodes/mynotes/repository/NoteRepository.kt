package com.swancodes.mynotes.repository

import androidx.lifecycle.LiveData
import com.swancodes.mynotes.data.Note

interface NoteRepository {

    val getAllNotes: LiveData<List<Note>>
    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun searchNote(searchQuery: String): LiveData<List<Note>>
}