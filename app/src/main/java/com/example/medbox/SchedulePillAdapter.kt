package com.example.medbox

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.medbox.utils.CalendarUtils

class SchedulePillAdapter(context: Context, events: List<SchedulePill>) : ArrayAdapter<SchedulePill>(context,0,events) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val event = getItem(position)

        var itemView = convertView
        if (itemView == null)
            itemView = LayoutInflater.from(context).inflate(R.layout.event_cell, parent, false)

        val eventCellTV = itemView?.findViewById<TextView>(R.id.eventCellTV)

        val eventTitle = "${event?.getName()} ${CalendarUtils.formattedTime(event?.getTime()!!)}"
        eventCellTV!!.text = eventTitle

        return itemView!!
    }
}