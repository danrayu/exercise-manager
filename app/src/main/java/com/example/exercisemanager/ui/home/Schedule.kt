package com.example.exercisemanager.ui.home

import com.example.exercisemanager.ui.exercises.Exercise
import com.example.exercisemanager.ui.groups.Group

data class ExerciseSchedule (
    val id: Int,
    var exercise: Exercise,
    var schedulePattern: String,
    val scheduleType: String,
    var referenceDate: Int
)

data class GroupSchedule (
    val id: Int,
    var group: Group,
    var schedulePattern: String,
    val scheduleType: String,
    var referenceDate: Int
)