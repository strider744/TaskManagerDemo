package com.strider.taskmanager.enums

enum class Priority(val priority: String, val id: Int) {
    NONE("None", 0),
    HIGH("High", 1),
    MEDIUM("Medium", 2),
    LOW("Low", 3),
}