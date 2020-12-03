package com.strider.taskmanager.preferenses

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.enumpref.enumOrdinalPref
import com.chibatching.kotpref.livedata.asLiveData
import com.strider.taskmanager.enums.SortOrder
import splitties.init.appCtx

object ApplicationPrefs : KotprefModel(appCtx) {
    var sortOrder by enumOrdinalPref(SortOrder.BY_DATE)
    val sortOrderLiveData = ApplicationPrefs.asLiveData(ApplicationPrefs::sortOrder)
    var hideCompleted by booleanPref(false)
    val hideCompletedLiveData = ApplicationPrefs.asLiveData(ApplicationPrefs::hideCompleted)
}