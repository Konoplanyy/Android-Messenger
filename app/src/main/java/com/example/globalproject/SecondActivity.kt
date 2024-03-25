package com.example.globalproject

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart()
    {
        super.onStart()

        Log.d("SecondActivity", "onStart()")
    }

    override fun onRestart()
    {
        super.onRestart()

        Log.d("SecondActivity", "onRestart()")
    }

    override fun onResume()
    {
        super.onResume()

        Log.d("SecondActivity", "onResume()")
    }

    override fun onPause()
    {
        super.onPause()

        Log.d("SecondActivity", "onPause()")
    }

    override fun onStop()
    {
        super.onStop()

        Log.d("SecondActivity", "onStop()")
    }

    override fun onDestroy()
    {
        super.onDestroy()

        Log.d("SecondActivity", "onDestroy()")
    }

    fun OnClick(view: View)
    {
        finish()
    }

}