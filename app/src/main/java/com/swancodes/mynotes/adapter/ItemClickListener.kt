package com.swancodes.mynotes.adapter

import com.swancodes.mynotes.data.Note

interface ItemClickListener {
    fun onItemClick(note: Note)
}
