package com.swancodes.mynotes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.swancodes.mynotes.data.Note
import com.swancodes.mynotes.data.Priority
import com.swancodes.mynotes.getOrAwaitValue
import com.swancodes.mynotes.repository.FakeNoteRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NoteViewModelTest {
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var noteViewModel: NoteViewModel

    private lateinit var noteRepository: FakeNoteRepository

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        // Set the main dispatcher for testing
        Dispatchers.setMain(testDispatcher)

        // Using a fake repository for testing
        noteRepository = FakeNoteRepository()
        noteViewModel = NoteViewModel(noteRepository)
    }

    @After
    fun cleanup() {
        // Reset the main dispatcher after testing
        Dispatchers.resetMain()
        // Cleanup any remaining coroutines
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test addNote function`() = testDispatcher.runBlockingTest {
        // Given a note
        val note = Note(1, "Title", "Content", System.currentTimeMillis(), Priority.HIGH)

        // When adding the note
        noteViewModel.addNote(note)

        // Then the note should be added to the repository
        val allNotes = noteViewModel.allNotes.getOrAwaitValue()
        assertTrue(allNotes.contains(note))
    }

    @Test
    fun `test updateNote function`() = testDispatcher.runBlockingTest {
        // Given a note in the repository
        val originalNote =
            Note(1, "Original Title", "Original Content", System.currentTimeMillis(), Priority.LOW)
        noteRepository.addNote(originalNote)

        // When updating the note
        val updatedNote =
            Note(1, "Updated Title", "Updated Content", System.currentTimeMillis(), Priority.LOW)
        noteViewModel.updateNote(updatedNote)

        // Then the note should be updated in the repository
        val allNotes = noteViewModel.allNotes.getOrAwaitValue()
        assertEquals(updatedNote, allNotes.first())
    }

    @Test
    fun `test deleteNote function`() = testDispatcher.runBlockingTest {
        // Given a note in the repository
        val noteToDelete = Note(
            1,
            "Title to Delete",
            "Content to Delete",
            System.currentTimeMillis(),
            Priority.MEDIUM
        )
        noteRepository.addNote(noteToDelete)

        // When deleting the note
        noteViewModel.deleteNote(noteToDelete)

        // Then the note should be removed from the repository
        val allNotes = noteViewModel.allNotes.getOrAwaitValue()
        assertFalse(allNotes.contains(noteToDelete))
    }

    @Test
    fun `test searchNote function`() = testDispatcher.runBlockingTest {
        // Given notes in the repository
        val note1 = Note(
            1,
            "Searchable Title",
            "Searchable Content",
            System.currentTimeMillis(),
            Priority.MEDIUM
        )
        val note2 =
            Note(2, "Another Title", "Another Content", System.currentTimeMillis(), Priority.LOW)
        noteRepository.addNote(note1)
        noteRepository.addNote(note2)

        // When searching for notes with a query
        val searchQuery = "Searchable"
        val searchResults = noteViewModel.searchNote(searchQuery).getOrAwaitValue()

        // Then only the matching note should be returned
        assertEquals(1, searchResults.size)
        assertEquals(note1, searchResults.first())
    }
}