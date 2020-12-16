package com.strider.taskmanager.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.strider.taskmanager.ui.details.TaskDetailsFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

//@ExperimentalCoroutinesApi
//class MainFragmentFactory
//@Inject
//constructor(private val listener: OnBackPressedListener): FragmentFactory(){
//    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
//        return when(className) {
//            TaskDetailsFragment::class.java.name -> TaskDetailsFragment(listener)
//            else -> super.instantiate(classLoader, className)
//        }
//    }
//}