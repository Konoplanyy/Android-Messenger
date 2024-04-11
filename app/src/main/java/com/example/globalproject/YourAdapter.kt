package com.example.globalproject
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class YourAdapter(private val dataList: List<YourData>, private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<YourAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val flagImageView: ImageView = itemView.findViewById(R.id.flag)
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val capitalTextView: TextView = itemView.findViewById(R.id.capital)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                val text = dataList[position].name
                val phone = dataList[position].capital
                itemClickListener.onItemClick(position, text, phone)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.messeger_user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]

        holder.flagImageView.setImageResource(item.flagResource)
        holder.nameTextView.text = item.name
        holder.capitalTextView.text = item.capital
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
//Data Class
data class YourData(val name: String, val capital: String, val flagResource: Int)


//Interface
interface ItemClickListener {
    fun onItemClick(position: Int, text:String, phone:String)
}