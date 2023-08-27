package com.swancodes.mynotes.repository

import android.util.Log
import com.swancodes.mynotes.data.Note
import com.swancodes.mynotes.data.Priority

class NoteRepositoryImpl : NoteRepository {

    private val notes: MutableList<Note> = mutableListOf(
        Note("Note 1", "This is a new note", System.currentTimeMillis(), Priority.LOW),
        Note("Note 2", "This is a new note", System.currentTimeMillis(), Priority.MEDIUM),
        Note("Note 3", "This is a new note", System.currentTimeMillis(), Priority.HIGH),
        Note("Note 4", "This is a new note", System.currentTimeMillis(), Priority.MEDIUM),
        Note("Note 5", "This is a new note", System.currentTimeMillis(), Priority.LOW),
        Note("Note 6", "This is a new note", System.currentTimeMillis(), Priority.HIGH)
    )

    override fun getAllNotes(): List<Note> {
        return notes.toList()
    }


    override fun addNote(note: Note) {
        notes.add(note)
        Log.d("NoteRepositoryImpl", "Note added: $note")
    }

    override fun deleteNote(note: Note) {
        notes.remove(note)
        Log.d("NoteRepositoryImpl", "Note deleted: $note")
    }
}