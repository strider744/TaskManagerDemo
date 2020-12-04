package com.strider.taskmanager.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import androidx.fragment.app.Fragment

fun Activity.hideKeyboard(view: View?) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, HIDE_IMPLICIT_ONLY)
}

fun Activity.showKeyboard(view: View?) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, SHOW_IMPLICIT)
}

fun Activity.toggleKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(SHOW_IMPLICIT, HIDE_IMPLICIT_ONLY)
}

fun Fragment.hideKeyboard(view: View?) {
    val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, HIDE_IMPLICIT_ONLY)
}

fun Fragment.showKeyboard(view: View?) {
    val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, SHOW_IMPLICIT)
}

fun Fragment.toggleKeyboard() {
    val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(SHOW_IMPLICIT, HIDE_IMPLICIT_ONLY)
}