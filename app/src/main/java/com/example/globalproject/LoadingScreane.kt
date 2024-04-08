package com.example.globalproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoadingScreane : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getWindow().setNavigationBarColor(getResources().getColor(R.color.MainColor));
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loading_screane)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        var sp = getSharedPreferences("PC", Context.MODE_PRIVATE)
        if (sp.getString("TY", "-9") != "-9")
        {
            startActivity(Intent(this, MessengerActivity::class.java))
            finish()
        }
        else{
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }


}