package com.example.exercisemanager.ui.groups

import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.ui.exercises.Exercise

class Group(
    id: Int,
    name: String,
    var description : String,
    var exercises : MutableList<Exercise>,
    var expanded: Boolean = false
) : DisplayableItem(id, name)