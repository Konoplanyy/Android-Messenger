package com.example.globalproject

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MessengerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger_activity)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }



        //set theme color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.MainColor))
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.MainColor)
        }

        var sp = getSharedPreferences("PC", MODE_PRIVATE)
//        val settingsFragment = supportFragmentManager.findFragmentById(R.id.) as SettingsFragment
        sp.edit().putString("TY", "9").commit()
//        var emailname: TextView = findViewById(R.id.textView11) as SettingsFragment
//        emailname.text = sp.getString("ID", "Don`t load")

        val fragmentManager: FragmentManager = supportFragmentManager

        val btnNavView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, ChatFragment()).commit()
        btnNavView.setOnItemSelectedListener {item ->

                lateinit var fragment: Fragment
                when (item.itemId) {
                    R.id.action_chat_menu -> fragment = ChatFragment()
                    R.id.action_contacts_menu -> fragment = ContactsFragment()
                    R.id.action_settings_menu -> fragment = SettingsFragment()
                }
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
                true
        }


    }

    fun Logout(view: View) {
        var sp = getSharedPreferences("PC", Context.MODE_PRIVATE)
        sp.edit().putString("TY", "-9").commit()
        startActivity(Intent(this, Login::class.java))
    }

    fun Contacts_add_btn(view: View){
        val modalBottomSheet = Contacts_Add_BS()
        modalBottomSheet.show(supportFragmentManager, Contacts_Add_BS.TAG)
    }


}