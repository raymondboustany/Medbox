package com.example.medbox


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medbox.utils.CalendarUtils
import com.example.medbox.utils.CalendarUtils.daysInWeekArray
import com.example.medbox.utils.CalendarUtils.monthYearFromDate
import java.time.LocalDate


class WeeklyViewActivity : AppCompatActivity(), CalendarAdapter.OnItemListener {

    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var eventListView: ListView


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week_view)
        initWidgets()
        CalendarUtils.selectedDate = LocalDate.now()
        setWeekView()

        val previousbtn = findViewById<Button>(R.id.previous_week_btn)
        val nextbtn = findViewById<Button>(R.id.next_week_btn)
        val newBtn = findViewById<Button>(R.id.new_evntBtn)

        previousbtn.setOnClickListener {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate?.minusWeeks(1)
            setWeekView() }

        nextbtn.setOnClickListener {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate?.plusWeeks(1)
            setWeekView() }

        newBtn.setOnClickListener {  startActivity(Intent(this, SecondFragment::class.java))}
    }

    private fun initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)
        eventListView = findViewById(R.id.eventListView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setWeekView() {
        //monthYearText?.setText(monthYearFromDate(CalendarUtils.selectedDate!!))

        val selectedDate = CalendarUtils.selectedDate
        if (selectedDate != null) {
            val monthYear = monthYearFromDate(selectedDate)
            monthYearText?.text = monthYear
        }

        val days: ArrayList<LocalDate> = daysInWeekArray(CalendarUtils.selectedDate!!)
        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
        setEventAdpater()
    }


    override fun onResume() {
        super.onResume()
        setEventAdpater()
    }

    private fun setEventAdpater() {
        val dailyEvents: ArrayList<SchedulePill> = SchedulePill.eventsForDate(CalendarUtils.selectedDate!!) as ArrayList<SchedulePill>
        val eventAdapter = SchedulePillAdapter(applicationContext, dailyEvents)
        eventListView!!.adapter = eventAdapter
    }



    /*fun newEventAction(view: View?) {
        startActivity(Intent(this, SecondFragment::class.java))
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, date: LocalDate) {
        CalendarUtils.selectedDate = date
        setWeekView()
    }
}