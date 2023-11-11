package com.swancodes.mynotes.repository

import androidx.lifecycle.LiveData
import com.swancodes.mynotes.data.Note
import com.swancodes.mynotes.data.NoteDao

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {

    override val getAllNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    override suspend fun addNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    override fun searchNote(searchQuery: String): LiveData<List<Note>> {
        return noteDao.searchNote(searchQuery)
    }
}