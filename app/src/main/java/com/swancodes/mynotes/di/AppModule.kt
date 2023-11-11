package com.swancodes.mynotes.di

import com.swancodes.mynotes.data.NoteDatabase
import com.swancodes.mynotes.repository.NoteRepository
import com.swancodes.mynotes.repository.NoteRepositoryImpl
import com.swancodes.mynotes.viewmodel.NoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// A Koin module is the place where we define all our components to be injected.
val appModule = module {
    single<NoteRepository> { NoteRepositoryImpl(get()) }
    viewModel { NoteViewModel(get()) }
    single { NoteDatabase.getDatabase(get()) }
    single { get<NoteDatabase>().noteDao() }
}