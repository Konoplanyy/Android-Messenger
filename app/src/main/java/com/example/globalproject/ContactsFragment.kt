package com.example.globalproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Timer
import java.util.TimerTask

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.Contacts_recyclerView)

        var db = Firebase.firestore
        var sp = requireActivity().getSharedPreferences("PC", Context.MODE_PRIVATE)
        recyclerView?.layoutManager = LinearLayoutManager(context)


        val dataList = mutableListOf<YourData>()

        db.collection("users/${sp.getString("user", "")}/Contacts").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val thirdData = YourData(
                        "${document.getString("Name")} ${document.getString("Surname")}",
                        document.getString("Phone").toString(),
                        R.drawable.smile_btn_img
                    )
                    dataList.add(thirdData)
                }

                val itemClickListener = object : ItemClickListener {
                    override fun onItemClick(position: Int, text:String, phone:String) {
                        val pos = recyclerView.get(position)
                        Toast.makeText(context, "Position $pos", Toast.LENGTH_SHORT).show()
                    }
                }

                recyclerView?.adapter = YourAdapter(dataList, itemClickListener)
            }




        return view
    }

    fun updateData() {
        // Виконайте код для оновлення даних тут
        println("Оновлення даних...")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}