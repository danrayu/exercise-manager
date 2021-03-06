package com.example.exercisemanager.ui.home

import com.example.exercisemanager.src.DisplayableItem
import org.threeten.bp.LocalDate

class Schedule(
    var id: Int,
    var schedulePattern: String,
    var displayableItems: MutableList<DisplayableItem>?,
    val scheduleType: String,
    var referenceDate: LocalDate
    ) {
    fun isScheduledAtDate(date: LocalDate) : Boolean{
        var isScheduled = false
        var differenceDays = date.compareTo(referenceDate)

        if (scheduleType == "Pattern") {
            val patternArray = schedulePattern.toCharArray()
            // removing unnecessary length to improve performance
            differenceDays %= patternArray.size
            for (day in patternArray) {
                if (differenceDays == 0) {
                    if (day == '1') {
                        isScheduled = true
                        break
                    }
                }
                differenceDays -= 1
            }
        }
        else if (scheduleType == "Weekly") {
            val daysInWeek = 7
            val patternArray = schedulePattern.toCharArray()
            // removing unnecessary length to improve performance
            differenceDays %= daysInWeek
            for (day in patternArray) {
                if (differenceDays == 0) {
                    if (day == '1') {
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
