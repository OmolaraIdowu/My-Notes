package com.swancodes.mynotes.repository

import com.swancodes.mynotes.data.Note

interface NoteRepository {

    fun getAllNotes(): List<Note>
    fun addNote(note: Note)
    fun deleteNote(note: Note)
}