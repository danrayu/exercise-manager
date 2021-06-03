package com.example.exercisemanager.ui.exercises

import com.example.exercisemanager.ui.muscles.Muscle

data class Exercise (
        var name: String,
        var description: String,
        var muscles: MutableList<Muscle>,
        var expand: Boolean = false,
        var id: Int
)