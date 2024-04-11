package com.example.globalproject

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.firestore.*

class Contacts_Add_BS : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.contacts_add_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val CancelBtn: TextView = view.findViewById(R.id.textView7)
        val SaveBtn: TextView = view.findViewById(R.id.textView9)

        val Name: TextView = view.findViewById(R.id.NameIdInput)
        val Surname: TextView = view.findViewById(R.id.SurnameIdInput)
        val Phone: TextView = view.findViewById(R.id.PhoneIdInput)


        CancelBtn.setOnClickListener {
            dismiss()
        }

        SaveBtn.setOnClickListener {
//            val Contact = hashMapOf(
//                "Name" to Name.text.toString(),
//                "Surname" to Surname.text.toString(),
//                "Phone" to Phone.text.toString()
//            )
//
//
//
//            db.collection("users/" + sp.getString("user", "") + "/Contacts")
//                .add(Contact)
//
//            dismiss()
            val db = Firebase.firestore
            var sp =  requireActivity().getSharedPreferences("PC", Context.MODE_PRIVATE)
            db.collection("users")
                .whereEqualTo("ID", Phone.text.toString())
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty && Phone.text.toString() != sp.getString("ID", "")) {
                        val Contact = hashMapOf(
                            "Name" to Name.text.toString(),
                            "Surname" to Surname.text.toString(),
                            "Phone" to Phone.text.toString()
                        )

                        db.collection("users/${sp.getString("user", "")}/Contacts")
                            .add(Contact)
                            .addOnSuccessListener {
                                // Успішно додано
                                dismiss()
                            }
                            .addOnFailureListener { e ->
                                Log.w("TAG", "Error adding document", e)
                                // Тут можна додати код для обробки помилки
                            }
                    } else {
                        Toast.makeText(requireContext(), "Такого номера не існує", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error getting documents", e)
                }
        }
    }
    companion object {
        const val TAG = "contacts_add_bottom_sheet"
    }
}