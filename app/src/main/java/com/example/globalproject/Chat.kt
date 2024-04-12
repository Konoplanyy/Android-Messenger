package com.example.globalproject

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
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

class Chat : AppCompatActivity() {
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

        val recyclerView = findViewById<RecyclerView>(R.id.Chat_recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this)

        val dataList = mutableListOf<ChatListData>()

        val messageList: MutableList<String> = mutableListOf()

        val adapter = Chat_Message_Adapter(this, messageList)
        recyclerView.adapter = adapter

        val newMessage = "Нове повідомлення"
        messageList.add(newMessage)
        adapter.notifyItemInserted(messageList.size - 1)

        adapter.notifyDataSetChanged()


//        db.collection("users/${sp.getString("user", "")}/Contacts").get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    val thirdData = ChatListData(
//                        "${document.getString("Name")} ${document.getString("Surname")}",
//                        document.getString("Phone").toString(),
//                        R.drawable.smile_btn_img
//                    )
//                    dataList.add(thirdData)
//                }
//
//
//                recyclerView?.adapter = ChatMessageAdapter(dataList)
//            }

        val talker_phone = intent.getStringExtra("Phone")

        val TextViewName:TextView = findViewById(R.id.textView7)
        TextViewName.text = talker_phone

        val backBtn:Button = findViewById(R.id.button6)
        backBtn.setOnClickListener {
            startActivity(Intent(this, MessengerActivity::class.java))
        }

    }
}