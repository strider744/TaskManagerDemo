package com.strider.taskmanager.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.*
import androidx.fragment.app.Fragment

fun Activity.hideKeyboard(view: View?) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Activity.showKeyboard(view: View?) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, SHOW_FORCED)
}

fun Activity.toggleKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(SHOW_FORCED, 0)
}

fun Fragment.hideKeyboard() {
    val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.view?.windowToken, 0)
}

fun Fragment.showKeyboard(view: View?) {
    val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, SHOW_FORCED)
}

fun Fragment.toggleKeyboard() {
    val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(SHOW_FORCED, HIDE_IMPLICIT_ONLY)
}