package com.swancodes.mynotes.data


enum class Priority { LOW, MEDIUM, HIGH }
data class Note(
    val title: String,
    val content: String,
    val creationTime: Long,
    val priority: Priority
)
