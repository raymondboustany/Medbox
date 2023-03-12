package com.example.medbox

import android.Manifest
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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class MainActivity : AppCompatActivity() {

    /*companion object {
        private val mUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    }

    @RequiresApi(Build.VERSION_CODES.S)
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


    private lateinit var binding : NotificationInterfaceBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NotificationInterfaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()
        binding.submitButton.setOnClickListener{ scheduleNotification() }
        binding.timePicker.setIs24HourView(true)
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
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date)  + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
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
        calendar.set(year,month,day,hour,minute)
        return calendar.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = " A Description of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance )
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel) // a channel to post the notif on

    }
}