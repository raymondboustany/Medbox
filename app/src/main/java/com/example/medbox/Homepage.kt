package com.example.medbox

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.medbox.databinding.ActivityHomepageBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Homepage : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomepageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)




        val navController = findNavController(R.id.nav_host_fragment_content_homepage) //content_homepage.xml
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration) //change navbar content

        //plus button bottom right corner
        /*binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
        binding.fab.setOnClickListener {
            findNavController(R.id.nav_host_fragment_content_homepage).navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    //when user uses device back button to navigate
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_homepage)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setImageResource(R.drawable.ic_add)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}