package com.example.globalproject

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.FrameStats
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

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

//        val bottomSheetFragment = Contacts_Add_BS()
//        bottomSheetFragment.setDataListener(this)
//        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)

        if (fragment != null) {
            val recyclerView = fragment.view?.findViewById<RecyclerView>(R.id.recyclerView)

            recyclerView?.apply {
                val dataList = listOf(
                    YourData("Sakane Miiko", "send your picture to me ! i want to use for your profile on web…", R.drawable.smile_btn_img),
                    YourData("Second Name", "Second capital", R.drawable.smile_btn_img),
                )

                recyclerView.adapter = YourAdapter(dataList)

            }
        } else {

        }



    }

//    override fun onDataReceived(Contact: HashMap<String, String>) {
//        val db = Firebase.firestore
//        var sp = getSharedPreferences("PC", MODE_PRIVATE)
//
//        db.collection("users/" + sp.getString("user", "") +"/Contacts")
//            .add(Contact)
//    }

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

data class YourData(val name: String, val capital: String, val flagResource: Int)

class YourAdapter(private val dataList: List<YourData>) : RecyclerView.Adapter<YourAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flagImageView: ImageView = itemView.findViewById(R.id.flag)
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val capitalTextView: TextView = itemView.findViewById(R.id.capital)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.messeger_user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]

        // Встановлюємо дані для кожного елемента списку
        holder.flagImageView.setImageResource(item.flagResource)
        holder.nameTextView.text = item.name
        holder.capitalTextView.text = item.capital
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}