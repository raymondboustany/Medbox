package com.example.medbox.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object CalendarUtils {
    var selectedDate: LocalDate? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun formattedDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formattedTime(time: LocalTime): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
        return time.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun daysInMonthArray(date: LocalDate): ArrayList<LocalDate?> {
        val daysInMonthArray = ArrayList<LocalDate?>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(null)
            } else {
                daysInMonthArray.add(
                    LocalDate.of(
                        selectedDate!!.year,
                        selectedDate!!.month,
                        i - dayOfWeek
                    )
                )
            }
        }
        return daysInMonthArray
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun daysInWeekArray(selectedDate: LocalDate): ArrayList<LocalDate> {
        val days = ArrayList<LocalDate>()
        var current = sundayForDate(selectedDate)
        val endDate = current?.plusWeeks(1)

        if (current != null) {
            while (current?.isBefore(endDate) == true) {
                days.add(current)
                current = current.plusDays(1)
            }
        }
        return days
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sundayForDate(current: LocalDate): LocalDate? {
        var oneWeekAgo = current.minusWeeks(1)

        var currentDate = current

        while (currentDate.isAfter(oneWeekAgo)) {
            //goes back to find sunday
            if (currentDate.dayOfWeek == DayOfWeek.SUNDAY) {
                return currentDate
            }
            currentDate = current.minusDays(1)
        }
        return null
    }
}