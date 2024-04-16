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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Chat : AppCompatActivity() {
    val MAX_MESSAGE_LENGTH = 200
    var messages = mutableListOf<DataMessage>()
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
        val path: String
        if (user_phone.toString().compareTo(talker_phone.toString()) < 0){
            path = user_phone.toString() + "-" + talker_phone.toString()
        }
        else{
            path = talker_phone.toString() + "-" + user_phone.toString()

        }
        val myRef = db.getReference(path)

        val TextViewName:TextView = findViewById(R.id.textView7)
        TextViewName.text = talker_name

        val backBtn:Button = findViewById(R.id.button6)
        backBtn.setOnClickListener {
            startActivity(Intent(this, MessengerActivity::class.java))
        }

        val EditText : EditText = findViewById(R.id.PhoneIdInput)
        val SendButton : Button = findViewById(R.id.SendButton)

        val chatAdapter = ChatAdapter(this)
        val recyclerView = findViewById<RecyclerView>(R.id.Chat_recyclerView)
        recyclerView.adapter = chatAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this)


        SendButton.setOnClickListener {
            val msg = EditText.text.toString()
            if (!msg.equals("")){
                if (msg.length < MAX_MESSAGE_LENGTH){
                    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val messageData = hashMapOf<String, String>(
                        "message" to msg,
                        "sender" to sp.getString("ID", "").toString(),
                        "timestamp" to dateFormat.format(Date())
                    )
                    myRef.push()
                        .setValue(messageData)

                    EditText.setText("")
                }
                else{
                    Toast.makeText(this, getString(R.string.Big_message), Toast.LENGTH_SHORT).show()
                }
            }
        }

        myRef.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                messages.clear()
                val msgText = snapshot.child("message").getValue(String::class.java).toString()
                val sender = snapshot.child("sender").getValue(String::class.java).toString()
                val time = snapshot.child("timestamp").getValue(String::class.java).toString()
                val msg = DataMessage(msgText, sender, time)
                messages += msg

                if (messages.isNotEmpty()) {
                    for (message in messages) {
                        if (message.sender == sp.getString("ID", ""))
                            chatAdapter.addMessage(message.message.toString(), message.timestamp.toString(), 1)
                        else
                            chatAdapter.addMessage(message.message.toString(), message.timestamp.toString(), 2)
                    }
                }
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
