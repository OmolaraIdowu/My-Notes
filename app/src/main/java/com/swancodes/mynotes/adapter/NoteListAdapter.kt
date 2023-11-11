package com.swancodes.mynotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swancodes.mynotes.R
import com.swancodes.mynotes.data.Note
import com.swancodes.mynotes.data.Priority
import com.swancodes.mynotes.databinding.NoteListItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteListAdapter(private val listener: ItemClickListener) :
    ListAdapter<Note, NoteListAdapter.NoteListViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        return NoteListViewHolder(
            NoteListItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.note_list_item, parent, false
                )
            )
        )
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteListViewHolder(private val binding: NoteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) = with(binding) {
            noteTitle.text = note.title
            noteContent.text = note.content

            val formattedTime = formatDate(note.creationTime)
            dateText.text = formattedTime
            setPriorityColor(note.priority)

            root.setOnClickListener {
                listener.onItemClick(note)
            }
        }

        private fun formatDate(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.getDefault())
            val date = Date(timestamp)
            return sdf.format(date)
        }

        private fun setPriorityColor(priority: Priority) {
            val drawableResource = when (priority) {
                Priority.LOW -> R.drawable.ic_green_flag
                Priority.MEDIUM -> R.drawable.ic_yellow_flag
                Priority.HIGH -> R.drawable.ic_red_flag
            }
            binding.priorityIndicator.setImageResource(drawableResource)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}