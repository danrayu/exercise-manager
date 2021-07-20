package com.example.exercisemanager.src

import org.threeten.bp.LocalDate

class Scheduler(db: DataBaseHandler) {
    private var schedules = db.readScheduleData(db.readableDatabase)

    fun showScheduledItemsToday() : MutableList<DisplayableItem> {
        val itemList: MutableList<DisplayableItem> = ArrayList()
        for (schedule in schedules) {
            if (schedule.isScheduled(LocalDate.now())) {
                itemList.add(schedule.item)
            }
        }
        return itemList
    }

}