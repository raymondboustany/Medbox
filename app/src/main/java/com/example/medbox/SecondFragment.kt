package com.example.medbox

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.medbox.databinding.FragmentSecondBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var timePickerContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        //false is passed for the attachToRoot parameter to indicate that the inflated layout should not be attached to the parent view because the FragmentTransaction will handle that.



        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val fab: FloatingActionButton = requireActivity().findViewById(R.id.fab)
        fab.setImageResource(R.drawable.ic_done)

        fab.setOnClickListener {
            fab.setImageResource(R.drawable.ic_add)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/

        binding.saveBtn.setOnClickListener {
            val medName = binding.editMedName.text.toString()
            val timesaday = binding.timesadaySpinner.selectedItem.toString()
            val nbofevents = timesaday.toInt()
            val dose = binding.tvDoseQuantity.text.toString().toInt()
            val type = binding.spinnerDoseUnits.selectedItem.toString()
            val stock = binding.inputMed2.text.toString().toInt()

            for (i in 0 until nbofevents){
                val timeTextView = view.findViewById<TextView>(i+1) // replace with your TextView id
                val timeString = timeTextView.text.toString()
                val time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"))

                val newEvent = SchedulePill(medName,dose, type,time,stock)
                SchedulePill.scheduledList.add(newEvent)
            }
            //need to finish and return to the previous activity

        }


        /*binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment) // actions defined in nav_graph.xml
        }*/

        val spinner2 = binding.timesadaySpinner
        val numbers2 = arrayOf("1", "2", "3", "4")
        val adapter2 =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, numbers2)
        spinner2.adapter = adapter2

        timePickerContainer = binding.timePickerContainer

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Remove any existing time pickers
                timePickerContainer.removeAllViews()

                // Get the selected value from the spinner
                val selectedValue = parent.getItemAtPosition(position) as String

                // Generate the corresponding number of time pickers
                val numTimePickers = selectedValue.toInt()
                for (i in 0 until numTimePickers) {
                    val container = LinearLayout(requireContext())
                    container.orientation = LinearLayout.HORIZONTAL
                    container.setPadding(0,20,0,40)
                    timePickerContainer.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
                    timePickerContainer.dividerDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.divider)

                    val title = TextView(requireContext())
                    title.text = "Time Picker ${i + 1}:"

                    val time_tv = TextView(requireContext())
                    time_tv.id = i + 1 //give an id to each time picker
                    time_tv.text = "select time ..."
                    time_tv.setBackgroundResource(R.drawable.drawable_underline)
                    time_tv.gravity = Gravity.CENTER
                    time_tv.setTypeface(null,Typeface.BOLD)
                    time_tv.setPadding(10,0,4, 0)

                    time_tv.setOnClickListener {
                      val mcurrentTime = Calendar.getInstance()
                        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                        val minute = mcurrentTime.get(Calendar.MINUTE)

                        val mTimePicker: TimePickerDialog = TimePickerDialog(requireContext(),
                            { _, selectedHour, selectedMinute ->
                                time_tv.setText("$selectedHour:$selectedMinute")
                            }, hour, minute, true) // Yes 24 hour time
                        mTimePicker.setTitle("Select Time")
                        mTimePicker.show()
                    }

                    /*val timePicker = TimePicker(requireContext())
                    timePicker.setIs24HourView(true)*/


                    //val spinner = createTimePickerSpinner()


                    container.addView(title)
                    container.addView(time_tv)

                    timePickerContainer.addView(container)

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    fun createTimePickerSpinner(): Spinner {
        val times = mutableListOf<String>()
        for (hour in 0..23) {
            for (minute in 0..59 step 1) {
                val time = String.format("%02d:%02d", hour, minute)
                times.add(time)
            }
        }

        val spinner = Spinner(requireContext())
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, times)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        return spinner
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
