package com.example.globalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
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
    }

}