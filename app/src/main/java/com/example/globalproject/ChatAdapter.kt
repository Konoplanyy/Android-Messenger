package com.example.globalproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val context: Context) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    data class Message(val text: String, val time: String, val messageType: Int)

    private val messages = ArrayList<Message>()

    // Типи повідомлень
    companion object {
        const val MESSAGE_TYPE_MY_MESSAGE = 1
        const val MESSAGE_TYPE_OTHER_MESSAGE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutRes = when (viewType) {
            MESSAGE_TYPE_MY_MESSAGE -> R.layout.message_chat_item
            MESSAGE_TYPE_OTHER_MESSAGE -> R.layout.message_chat_item_abonent
            else -> throw IllegalArgumentException("Invalid message type")
        }
        val view = LayoutInflater.from(context).inflate(layoutRes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        holder.messageText.text = message.text
        holder.timeText.text = message.time
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        return messages[position].messageType
    }

    fun addMessage(text: String, time: String, messageType: Int) {
        messages.add(Message(text, time, messageType))
        notifyItemInserted(messages.size - 1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.messageText)
        val timeText: TextView = itemView.findViewById(R.id.TimeText)
    }
}
