package com.example.globalproject

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database

class Chat : AppCompatActivity() {
    val MAX_MESSAGE_LENGTH = 200
    var messages = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        //set theme color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.MainColor))
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.MainColor)
        }
        var sp = getSharedPreferences("PC", Context.MODE_PRIVATE)
        val talker_phone = intent.getStringExtra("Phone")
        val talker_name = intent.getStringExtra("Name")
        val user_phone = sp.getString("ID", "")
        val db = Firebase.database
        val myRef = db.getReference(user_phone + "-" + talker_phone)

        val TextViewName:TextView = findViewById(R.id.textView7)
        TextViewName.text = talker_name

        val backBtn:Button = findViewById(R.id.button6)
        backBtn.setOnClickListener {
            startActivity(Intent(this, MessengerActivity::class.java))
        }

        val EditText : EditText = findViewById(R.id.PhoneIdInput)
        val SendButton : Button = findViewById(R.id.SendButton)

        SendButton.setOnClickListener {
            val msg = EditText.text.toString()
            if (!msg.equals("")){
                  if (msg.length < MAX_MESSAGE_LENGTH){
                      myRef.push()
                          .setValue(msg)

                      EditText.setText("")
                  }
                else{
                      Toast.makeText(this, getString(R.string.Big_message), Toast.LENGTH_SHORT).show()
                  }
            }
        }

        myRef.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val msg = snapshot.getValue(String::class.java).toString()
                messages += msg
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}