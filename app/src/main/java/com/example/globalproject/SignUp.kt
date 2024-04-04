package com.example.globalproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.security.MessageDigest

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getWindow().setNavigationBarColor(getResources().getColor(R.color.MainColor));
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var sp = getSharedPreferences("PC", Context.MODE_PRIVATE).edit()
        var ID: TextView = findViewById(R.id.PhoneIdInput)
        var password: TextView = findViewById(R.id.PasswordInput)
        var ConfirmPassword: TextView = findViewById(R.id.PasswordConfirmInput)
        var button: Button = findViewById(R.id.LoginButton)
        button.setOnClickListener {
            if (password.text.toString() != ConfirmPassword.text.toString()) {
                Toast.makeText(this, getString(R.string.passwords_do_not_match), Toast.LENGTH_LONG)
                    .show()
            }else if (ID.text.isEmpty() || !ID.text.contains('+')) {
                Toast.makeText(
                    this,
                    getString(R.string.Incorrect_phone_number_entered),
                    Toast.LENGTH_LONG
                ).show()
            }else if(password.text.isEmpty() || password.text.length < 6) {
                Toast.makeText(this, getString(R.string.short_password), Toast.LENGTH_LONG).show()
            }else{
                val db = Firebase.firestore


                val user = hashMapOf(
                    "ID" to ID.text.toString(),
                    "password" to password.text.toString().hashCode().toString()
                )

                db.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        sp.putString("ID", ID.text.toString()).commit()
                        startActivity(Intent(this, MessengerActivity::class.java))
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, getString(R.string.error_try_leter), Toast.LENGTH_LONG).show()
                    }
            }
        }

    }
}