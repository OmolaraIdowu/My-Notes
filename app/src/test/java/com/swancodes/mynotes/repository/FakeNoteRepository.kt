package com.swancodes.mynotes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.swancodes.mynotes.data.Note

class FakeNoteRepository : NoteRepository {

    private val notes = mutableListOf<Note>()
    private val observableNotes = MutableLiveData<List<Note>>()

    override val getAllNotes: LiveData<List<Note>>
        get() = observableNotes

    override suspend fun addNote(note: Note) {
        notes.add(note)
        observableNotes.value = notes.toList()
    }

    override suspend fun updateNote(note: Note) {
        val existingNote = notes.find { it.id == note.id }
        if (existingNote != null) {
            notes.remove(existingNote)
            notes.add(note)
            observableNotes.value = notes.toList()
        } else {
            throw NoSuchElementException("Note with id ${note.id} not found")
        }
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
        observableNotes.value = notes.toList()
    }

    override fun searchNote(searchQuery: String): LiveData<List<Note>> {
        val filteredList = notes.filter {
            it.title.contains(searchQuery, ignoreCase = true) || it.content.contains(
                searchQuery,
                ignoreCase = true
            )
        }
        return MutableLiveData(filteredList)
    }
}