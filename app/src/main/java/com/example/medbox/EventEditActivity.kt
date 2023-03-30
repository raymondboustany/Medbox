package com.example.medbox


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.medbox.utils.CalendarUtils
import java.time.LocalTime


class EventEditActivity : AppCompatActivity() {

    var eventNameET: EditText? = null
    var eventDateTV: TextView? = null
    var eventTimeTV:TextView? = null

    private lateinit var time: LocalTime

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV?.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate!!));
        eventTimeTV?.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private fun initWidgets() {
        eventNameET = findViewById<EditText>(R.id.eventNameET)
        eventDateTV = findViewById<TextView>(R.id.eventDateTV)
        eventTimeTV = findViewById<TextView>(R.id.eventTimeTV)
    }

    /*fun saveEventAction(view: View?) {
        val eventName = eventNameET!!.text.toString()
        val newEvent = Event(eventName, CalendarUtils.selectedDate, time)
        Event.eventsList.add(newEvent)
        finish()
    }*/
}