package com.swancodes.mynotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swancodes.mynotes.data.Note
import com.swancodes.mynotes.repository.NoteRepository

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        _notes.value = noteRepository.getAllNotes()
    }

    fun addNote(note: Note) {
        noteRepository.addNote(note)
        getAllNotes()
    }

    fun deleteNote(note: Note) {
        noteRepository.deleteNote(note)
        getAllNotes()
    }
}