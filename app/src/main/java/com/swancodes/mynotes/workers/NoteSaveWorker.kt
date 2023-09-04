package com.swancodes.mynotes.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.swancodes.mynotes.data.Note
import com.swancodes.mynotes.data.Priority
import com.swancodes.mynotes.repository.NoteRepository
import com.swancodes.mynotes.repository.NoteRepositoryImpl


// This is where you put the code for the actual work you want to perform in the background.
// You'll extend this class and override the doWork() method.
class NoteSaveWorker(
    ctx: Context,
    params: WorkerParameters,
) : Worker(ctx, params) {

    override fun doWork(): Result {
        val appContext = applicationContext
        val noteRepository: NoteRepository = NoteRepositoryImpl()
        try {
            saveNote(noteRepository)

            // Calculate size and show a success notification
            val noteSize = noteRepository.getAllNotes().size
            val successMessage = "Note saved successfully. Total Notes: $noteSize"
            showNotification(successMessage, appContext)

            // Create output data to pass information back to the caller
            val outputData = workDataOf("totalNotesSize" to noteSize)
            return Result.success(outputData)

        } catch (t: Throwable) {
            // Show an error notification with the exception message
            val errorMessage = "Note saving failed: ${t.message}"
            showNotification(errorMessage, appContext)

            Log.e(TAG, "Error while saving notes, ${t.message}")
            return Result.retry()
        }
    }

    private fun saveNote(noteRepository: NoteRepository) {
        // Save a note
        val newNote =
            Note("New Note", "This is a new note.", System.currentTimeMillis(), Priority.LOW)
        noteRepository.addNote(newNote)
        Log.d(TAG, "Note added: $newNote")

        // Save a list of notes
        //noteRepository.getAllNotes()
    }

    companion object {
        private const val TAG = "NoteSaveWorker"
    }
}