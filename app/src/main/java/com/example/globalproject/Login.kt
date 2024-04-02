package com.example.globalproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getWindow().setNavigationBarColor(getResources().getColor(R.color.MainColor));
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var sp = getSharedPreferences("PC", Context.MODE_PRIVATE).edit()
        var ID:TextView = findViewById(R.id.PhoneIdInput)
        var password:TextView = findViewById(R.id.PasswordInput)
        var button:Button = findViewById(R.id.LoginButton)
        var db = Firebase.firestore
        var df = false
        button.setOnClickListener {
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (document.getString("ID") == ID.text.toString()){
                            if (document.getString("password") == password.text.toString()){
                                df = true
                                sp.putString("ID", ID.text.toString()).commit()
                                startActivity(Intent(this, MessengerActivity::class.java))
                            }
                        }
                    }
                    if (!df){
                        Toast.makeText(this, getString(R.string.eror_pass_ID), Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, getString(R.string.error_try_leter), Toast.LENGTH_LONG).show()
                }

        }
    }

    override fun onBackPressed() {

    }

    fun onSingUpClick(view: View) {
        startActivity(Intent(this, SignUp::class.java))
    }

    fun StartMainActivity(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
