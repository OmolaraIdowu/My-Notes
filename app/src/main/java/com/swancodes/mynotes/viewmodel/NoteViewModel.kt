package com.swancodes.mynotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swancodes.mynotes.data.Note
import com.swancodes.mynotes.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val allNotes: LiveData<List<Note>> = noteRepository.getAllNotes

    fun addNote(note: Note) {
        viewModelScope.launch {
            noteRepository.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    fun searchNote(searchQuery: String): LiveData<List<Note>> {
        return noteRepository.searchNote(searchQuery)
    }
}