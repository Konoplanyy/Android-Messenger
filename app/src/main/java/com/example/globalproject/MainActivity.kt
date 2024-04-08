package com.example.globalproject

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        getWindow().setNavigationBarColor(getResources().getColor(R.color.MainColor));
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textView = findViewById(R.id.textView5)



    }

    override fun onStart()
    {
        super.onStart()

        Log.d("MainActivity", "onStart()")
    }

    override fun onRestart()
    {
        super.onRestart()

        Log.d("MainActivity", "onRestart()")
    }

    override fun onResume()
    {
        super.onResume()

        Log.d("MainActivity", "onResume()")
    }

    override fun onPause()
    {
        super.onPause()

        Log.d("MainActivity", "onPause()")
    }

    override fun onStop()
    {
        super.onStop()

        Log.d("MainActivity", "onStop()")
    }

    override fun onDestroy()
    {
        super.onDestroy()

        Log.d("MainActivity", "onDestroy()")
    }

    fun Add(view: View)
    {
        val Login = Intent(this, Login::class.java)
        startActivity(Login)
    }

    fun Click(view: View)
    {
        val checkedRadioButtonId = findViewById<RadioGroup>(R.id.radioGroup2).checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(checkedRadioButtonId)
        textView.text = "Your choice: ${radioButton.text}"
        showNotification("Hello", "user")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.settings -> {
                val toast = Toast.makeText(
                    applicationContext,
                    "Clicked: settings", Toast.LENGTH_SHORT
                )
                toast.show()
                true
            }
            R.id.help -> {
                val toast = Toast.makeText(
                    applicationContext,
                    "Clicked: help", Toast.LENGTH_SHORT
                )
                toast.show()

                true
            }
            R.id.about -> {
                val toast = Toast.makeText(
                    applicationContext,
                    "Clicked: about", Toast.LENGTH_SHORT
                )
                toast.show()
//                showDialog()
//                ShowNofication()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showNotification(msg:String?, user:String?) {
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val NOTIFICATION_CHANNEL_ID = "channel_id_01"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "My_Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = "Channel description"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(user)
                .setContentText(msg)
        val notificationIntent = Intent(this, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE //FLAG_CANCEL_CURRENT
        )
        builder.setContentIntent(contentIntent)
        val notification: Notification = builder.build()
        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL
        notificationManager.notify(4, notification)
    }

}