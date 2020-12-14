package com.strider.taskmanager.enums

enum class Priority(val priority: String, val id: Int) {
    NONE("None", 0),
    LOW("Low", 1),
    MEDIUM("Medium", 2),
    HIGH("High", 3),
}