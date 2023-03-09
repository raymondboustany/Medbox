package com.example.medbox
/*


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addmedicine)


        // Find the spinner view in your layout file
        val spinner = findViewById<Spinner>(R.id.spinner)
        val spinner1 = findViewById<Spinner>(R.id.spinner1)
        val spinner2 = findViewById<Spinner>(R.id.spinner2)

        // Create an array to display in the spinner
        val numbers = arrayOf("1", "2", "3", "4", "5")
        val numbers1 = arrayOf("every day", "every 2 days", "every 3 days", "every 4 days")
        val numbers2 = arrayOf("1", "2", "3", "4", "5")

        // Create an ArrayAdapter using the numbers array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, numbers)
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, numbers1)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, numbers2)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner.adapter = adapter
        spinner1.adapter = adapter1
        spinner2.adapter = adapter2
    }
}


 */