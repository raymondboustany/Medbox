package com.example.medbox

import java.time.LocalDate
import java.time.LocalTime

class SchedulePill(
    private var pillName: String,
    private var doseQuantity: Int,
    private var type: String,
    private var time: LocalTime,
    private var nbStock: Int
) {
    companion object{
        val scheduledList = arrayListOf<SchedulePill>()

        fun eventsForDate(date: LocalDate): List<SchedulePill> {
            val events = ArrayList<SchedulePill>()
            for (event in scheduledList) { //add all events everyday
                    events.add(event)
            }
            return events
        }
    }

    fun getName(): String {
        return pillName
    }

    fun setName(name: String) {
        this.pillName = name
    }


    fun getDose(): Int {
        return doseQuantity
    }

    fun setDose(dose: Int) {
        this.doseQuantity = dose
    }

    fun getType(): String {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }

    fun getTime(): LocalTime {
        return time
    }

    fun setTime(time: LocalTime) {
        this.time = time
    }

    fun getNbStock(): Int {
        return nbStock
    }

    fun setNbStock(nb: Int) {
        this.nbStock = nb
    }
}