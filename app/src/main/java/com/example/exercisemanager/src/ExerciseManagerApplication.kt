package com.example.exercisemanager.src

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class ExerciseManagerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}