package com.example.medbox



import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.medbox.databinding.ActivityMainBinding
import com.example.medbox.databinding.NotificationInterfaceBinding
import java.util.*
import android.widget.EditText
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    companion object {
        private val mUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    }

    /*@RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val btAdapter = bluetoothManager.adapter

        Log.d("Bluetooth devices found", btAdapter?.bondedDevices.toString())

        val hc05: BluetoothDevice = btAdapter?.getRemoteDevice("FC:A8:9A:00:07:98")!!
        //println(hc05.name)
        Log.d("Bluetooth device Name", hc05.name.toString())

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH), 1)
        } else {
            // Code that needs the permission can be moved here
            var btSocket: BluetoothSocket? = null
            var counter = 0
            do {
                try {
                    btSocket = hc05.createRfcommSocketToServiceRecord(mUUID)

                    //println(btSocket)
                    Log.d("socket created", btSocket.toString())
                    btSocket.connect()
                    //println(btSocket.isConnected)
                    Log.d("check connection", "Is connected: " + btSocket.isConnected())

                } catch (e: IOException) {
                    e.printStackTrace()
                }
                counter++
            } while (!btSocket?.isConnected!! && counter < 3)

            try {
                val outputStream: OutputStream = btSocket.outputStream
                Log.d("connection before write", "Is connected: " + btSocket.isConnected())
                outputStream.write(48)

            } catch (e: IOException) {
                e.printStackTrace()
            }

            var inputStream: InputStream? = null
            try {

                inputStream = btSocket.inputStream
                //inputStream.skip(inputStream.available().toLong())
                Log.d("connection beforeRead", "Is connected: " + btSocket.isConnected())
                while (true) {
                    // Check if there is data available in the input stream
                    if (inputStream.available() > 0) {
                        /*val b: Byte = inputStream.read().toByte()
                        Log.d("msgArduino", b.toInt().toChar().toString())*/
                        val buffer = ByteArray(1024)
                        val bytes = inputStream.read(buffer)

                        // Convert the ByteArray to a String
                        val message = String(buffer, 0, bytes)
                        Log.d("msgArduino", message)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
            // Make sure to close the input stream when you're done with it
            inputStream?.close()
            }


        try {
                btSocket.close()
                Log.d("check connection", "Is connected: " + btSocket.isConnected())
            } catch (e: IOException) {
                e.printStackTrace()
            }


        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Code that needs the permission can be moved here
                Log.d("Permission","granted")
            }
        } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
    }*/


/*class MainActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var age: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shared_preferences_demo)
        name = findViewById(R.id.edit1)
        age = findViewById(R.id.edit2)
    }

    // Fetch the stored data in onResume() Because this is what will be called when the app opens again
    override fun onResume() {
        super.onResume()
        // Fetching the stored data from the SharedPreference
        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val s1 = sh.getString("name", "")
        val a = sh.getInt("age", 0)

        // Setting the fetched data in the EditTexts
        name.setText(s1)
        age.setText(a.toString())
    }

    // Store the data in the SharedPreference in the onPause() method
    // When the user closes the application onPause() will be called and data will be stored
    override fun onPause() {
        super.onPause()
        // Creating a shared pref object with a file name "MySharedPref" in private mode
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("name", name.text.toString())
        myEdit.putInt("age", age.text.toString().toInt())
        myEdit.apply()
    }
 */


    private lateinit var binding: NotificationInterfaceBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NotificationInterfaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()
        binding.timePicker.setIs24HourView(true)


        // BLUETOOTH CONNECTION
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val btAdapter = bluetoothManager.adapter

        Log.d("Bluetooth devices found", btAdapter?.bondedDevices.toString())

        val hc05: BluetoothDevice = btAdapter?.getRemoteDevice("FC:A8:9A:00:07:98")!!
        //println(hc05.name)
        Log.d("Bluetooth device Name", hc05.name.toString())

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH), 1)
        } else {
            // Code that needs the permission can be moved here
            var btSocket: BluetoothSocket? = null
            var counter = 0
            do {
                try {
                    btSocket = hc05.createRfcommSocketToServiceRecord(mUUID)

                    //println(btSocket)
                    Log.d("socket created", btSocket.toString())
                    btSocket.connect()
                    //println(btSocket.isConnected)
                    Log.d("check connection", "Is connected: " + btSocket.isConnected())

                } catch (e: IOException) {
                    e.printStackTrace()
                }
                counter++
            } while (!btSocket?.isConnected!! && counter < 3)

            //on click we schedule a notif and send the data to arduino
            binding.submitButton.setOnClickListener {
                scheduleNotification()
                sendData(btSocket)
            }


        }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun sendData(btSocket: BluetoothSocket) {
        try {
            val outputStream: OutputStream = btSocket.outputStream
            Log.d("connection before write", "Is connected: " + btSocket.isConnected())
            val time = getTime()
            val date = Date(time)
            val dateTime = SimpleDateFormat("HH:mm:ss").format(date) //convert date and time to string
            val totalmessage = dateTime + ",2,1"
            outputStream.write(totalmessage.toByteArray())

        } catch (e: IOException) {
            e.printStackTrace()
        }

        var inputStream: InputStream? = null
        try {

            inputStream = btSocket.inputStream
            //inputStream.skip(inputStream.available().toLong())
            Log.d("connection beforeRead", "Is connected: " + btSocket.isConnected())
            while (inputStream.available() > 0) {
                // Check if there is data available in the input stream

                    /*val b: Byte = inputStream.read().toByte()
                    Log.d("msgArduino", b.toInt().toChar().toString())*/
                    val buffer = ByteArray(1024)
                    val bytes = inputStream.read(buffer)

                    // Convert the ByteArray to a String
                    val message = String(buffer, 0, bytes)
                    Log.d("msgArduino", message)

            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            // Make sure to close the input stream when you're done with it
            inputStream?.close()
        }

        try {
            btSocket.close()
            Log.d("check connection", "Is connected: " + btSocket.isConnected())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = binding.TitleET.text.toString()
        val message = binding.messageET.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time) //time = getTime()
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date)
            )
            .setPositiveButton("Okay") { _, _ -> }
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getTime(): Long {
        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = " A Description of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel) // a channel to post the notif on
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Code that needs the permission can be moved here
                Log.d("Permission","granted")
            }
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }




    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.fragment_add_medicine)

        val myCheckBox = findViewById<AppCompatCheckBox>(R.id.every_day)
        val timepicker = findViewById<TimePicker>(R.id.timePicker)
        timepicker.setIs24HourView(true)

        if(myCheckBox.isChecked){

            disableCheckButtons()
        }else{
            enableCheckButtons()
        }




        /*// Find the spinner view in your layout file
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
        spinner2.adapter = adapter2*/

    }

    private fun enableCheckButtons() {
        val sundayChk= findViewById<AppCompatCheckBox>(R.id.dv_sunday)
        val mondayChk= findViewById<AppCompatCheckBox>(R.id.dv_monday)
        val tuesdayChk= findViewById<AppCompatCheckBox>(R.id.dv_tuesday)
        val wednesdayChk= findViewById<AppCompatCheckBox>(R.id.dv_wednesday)
        val thursdayChk= findViewById<AppCompatCheckBox>(R.id.dv_thursday)
        val fridayChk= findViewById<AppCompatCheckBox>(R.id.dv_friday)
        val saturdayChk= findViewById<AppCompatCheckBox>(R.id.dv_saturday)

        sundayChk.isEnabled = true
        mondayChk.isEnabled = true
        tuesdayChk.isEnabled = true
        wednesdayChk.isEnabled = true
        thursdayChk.isEnabled = true
        fridayChk.isEnabled = true
        saturdayChk.isEnabled = true
    }

    private fun disableCheckButtons() {
        val sundayChk= findViewById<AppCompatCheckBox>(R.id.dv_sunday)
        val mondayChk= findViewById<AppCompatCheckBox>(R.id.dv_monday)
        val tuesdayChk= findViewById<AppCompatCheckBox>(R.id.dv_tuesday)
        val wednesdayChk= findViewById<AppCompatCheckBox>(R.id.dv_wednesday)
        val thursdayChk= findViewById<AppCompatCheckBox>(R.id.dv_thursday)
        val fridayChk= findViewById<AppCompatCheckBox>(R.id.dv_friday)
        val saturdayChk= findViewById<AppCompatCheckBox>(R.id.dv_saturday)

        sundayChk.isChecked = false
        mondayChk.isChecked = false
        tuesdayChk.isChecked = false
        wednesdayChk.isChecked = false
        thursdayChk.isChecked = false
        fridayChk.isChecked = false
        saturdayChk.isChecked = false

    }*/
}



/*import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.medbox.databinding.DatabaseImplBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: DatabaseImplBinding

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DatabaseImplBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var lv: ListView
        var list = ArrayList<Any>()
        var adapter: ArrayAdapter<Any>

        lv = binding.listviewmeds;


        // below code is to add on click
        // listener to our add name button
        binding.addName.setOnClickListener{

            // below we have created
            // a new DBHelper class,
            // and passed context to it
            val db = DBHelper(this, null)

            // creating variables for values
            // in name and age edit texts
            val name = binding.enterName.text.toString()
            val age = binding.enterAge.text.toString()

            // calling method to add
            // name to our database
            db.addName(name, age)

            // Toast to message on the screen
            Toast.makeText(this, name + " added to database", Toast.LENGTH_LONG).show()

            // at last, clearing edit texts
            binding.enterName.text.clear()
            binding.enterAge.text.clear()
        }

        // below code is to add on click
        // listener to our print name button
        binding.printName.setOnClickListener{

            // creating a DBHelper class
            // and passing context to it
            val db = DBHelper(this, null)

            // below is the variable for cursor
            // we have called method to get
            // all names from our database
            // and add to name text view
            val cursor = db.getName()

            list.clear()

            if (cursor != null) {
                if (cursor.count == 0) {
                    Toast.makeText(this@MainActivity, "No Data", Toast.LENGTH_SHORT).show()
                } else {
                    cursor!!.moveToFirst()
                    list.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
                    list.add(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL)))
                    while (cursor.moveToNext()) {
                        list.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
                        list.add(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL)))

                    }
                    adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, list)
                    lv.adapter = adapter
                }
                cursor.close()
            }

            // moving the cursor to first position and
            // appending value in the text view
            /*cursor!!.moveToFirst()
            binding.Name.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)) + "\n")
            binding.Age.append(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL)) + "\n")*/

            // moving our cursor to next
            // position and appending values
            /*while(cursor.moveToNext()){
                binding.Name.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)) + "\n")
                binding.Age.append(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL)) + "\n")
            }*/

            // at last we close our cursor

        }
    }*/




