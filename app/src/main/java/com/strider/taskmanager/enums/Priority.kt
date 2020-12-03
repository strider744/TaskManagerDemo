package com.strider.taskmanager.enums

enum class Priority(val priority: String, val id: Int) {
    NONE("", 0),
    LOW("LOW", 1),
    MEDIUM("MEDIUM", 2),
    HIGH("HIGH", 3),
}