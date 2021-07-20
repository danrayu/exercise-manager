package com.example.exercisemanager.ui.home

import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.ui.exercises.Exercise
import com.example.exercisemanager.ui.groups.Group
import org.threeten.bp.LocalDate

class Schedule (
    val id: Int,
    var schedulePattern: String,
    var item: DisplayableItem,
    val scheduleType: String,
    var referenceDate: LocalDate
    ) {
    fun isScheduled(date: LocalDate) : Boolean{
        var isScheduled = false
        var differenceDays = date.compareTo(referenceDate)

        if (scheduleType == "pattern") {
            val patternArray = schedulePattern.split("")
            while (differenceDays > patternArray.size) {
                differenceDays -= patternArray.size
            }
            for (day in patternArray) {
                if (differenceDays == 0) {
                    if (day == "1") {
                        isScheduled = true
                        break
                    }
                }
                differenceDays -= 1
            }
        }
        else if (scheduleType == "weekly") {
            val daysInWeek = 7
            val patternArray = schedulePattern.split(",")
            while (differenceDays > daysInWeek) {
                differenceDays -= daysInWeek
            }
            for (day in patternArray) {
                if (differenceDays == 0) {
                    if (day == "1") {
                        isScheduled = true
                        break
                    }
                }
                differenceDays -= 1
            }
        }
        return isScheduled
    }
}
