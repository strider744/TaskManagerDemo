package com.strider.taskmanager.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

inline fun <T> LiveData<T>.observeNotNull(lifecycleOwner: LifecycleOwner, crossinline observer: (T) -> Unit) =
    observe(lifecycleOwner, { it?.let { observer(it) } })