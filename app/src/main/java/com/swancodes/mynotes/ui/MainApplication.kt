package com.swancodes.mynotes.ui

import android.app.Application
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.swancodes.mynotes.di.appModule
import com.swancodes.mynotes.workers.NoteSaveWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }

        scheduleNoteSaveWork()
    }

    private fun scheduleNoteSaveWork() {
        val constraints = Constraints.Builder()
            .build()

        val noteSaveWorkRequest = PeriodicWorkRequestBuilder<NoteSaveWorker>(
            10, TimeUnit.SECONDS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(noteSaveWorkRequest)
    }
}