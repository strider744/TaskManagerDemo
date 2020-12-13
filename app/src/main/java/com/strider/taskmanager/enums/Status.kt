package com.strider.taskmanager.enums

enum class Status(val status: String, val id: Int) {
    NONE("None", 0),
    TODO("Todo", 1),
    WIP("Wip", 2),
    REVIEW("Review", 3),
    DECLINED("Declined", 4),
}