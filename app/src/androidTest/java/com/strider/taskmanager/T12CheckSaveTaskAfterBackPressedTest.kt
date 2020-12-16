package com.strider.taskmanager

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.strider.taskmanager.enums.Priority
import com.strider.taskmanager.enums.Status
import com.strider.taskmanager.ui.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class T12CheckSaveTaskAfterBackPressedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    private lateinit var taskName: String
    private lateinit var taskDescription: String

    @Before
    fun before() {
        taskName = Random.nextInt().toString()
        taskDescription = Random.nextLong().toString() + Random.nextLong().toString()
    }

    // сохранение задачи после нажатия системной кнопки назад
    @Test
    fun t12CheckSaveTaskAfterBackPressedTest() {
        createTask()

        onView(withId(R.id.spinner_task_status)).check(matches(isDisplayed())).perform(click())
        onView(withText(Status.TODO.status)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.spinner_task_priority)).check(matches(isDisplayed())).perform(click())
        onView(withText(Priority.HIGH.priority)).check(matches(isDisplayed())).perform(click())

        closeSoftKeyboard()
        Espresso.pressBack()
        onView(withText(taskName).also {
            withChild(withText(Status.TODO.status))
        }).check(matches(isDisplayed()))
        onView(withText(taskName).also {
            withText(Priority.HIGH.priority)
        }).check(matches(isDisplayed()))
        onView(withText(taskName)).check(matches(isDisplayed())).perform(click())
        onView(withText(taskDescription)).check(matches(isDisplayed()))
    }

    // создание задачи
    private fun createTask() {
        onView(withId(R.id.btn_add))
            .check(matches(isDisplayed())).perform(ViewActions.click())
        onView(withId(R.id.et_task_title))
            .check(matches(isDisplayed())).perform(
                ViewActions.typeText(taskName)
            )
        onView(withId(R.id.et_task_description))
            .check(matches(isDisplayed()))
            .perform(ViewActions.typeText(taskDescription))
    }
}