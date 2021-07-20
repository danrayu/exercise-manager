package com.example.exercisemanager.ui.exercises

import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.ui.muscles.Muscle

class Exercise (
        name: String,
        var description: String,
        var muscles: MutableList<Muscle>,
        var expand: Boolean = false,
        id: Int
) : DisplayableItem(id, name)