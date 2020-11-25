package com.strider.taskmanager.enums

enum class Status(val status: String, val id: Int) {
    NONE("", 0),
    TODO("TODO", 1),
    WIP("WIP", 2),
    REVIEW("REVIEW", 3),
    DECLINED("DECLINED", 4),
}