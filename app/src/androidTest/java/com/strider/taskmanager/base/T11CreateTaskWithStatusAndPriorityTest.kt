package com.strider.taskmanager.base

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.strider.taskmanager.R
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
class T11CreateTaskWithStatusAndPriorityTest {

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

    // создаем задачу состатусом и проверяем её отображение
    @Test
    fun t11CreateTaskWithStatusAndPriorityTest() {
        createTask()
        Espresso.onView(ViewMatchers.withId(R.id.spinner_task_status))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(Status.TODO.status))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spinner_task_priority))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(Priority.HIGH.priority))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        saveAndCheckCreatedTaskTest()
        Espresso.onView(ViewMatchers.withText(taskName).also {
            ViewMatchers.withChild(ViewMatchers.withText(Status.TODO.status))
        }).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(taskName).also {
            ViewMatchers.withText(Priority.HIGH.priority)
        }).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    // создание задачи
    private fun createTask() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_add))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.et_task_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(
            ViewActions.typeText(taskName)
        )
        Espresso.onView(ViewMatchers.withId(R.id.et_task_description))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.typeText(taskDescription))
    }

    // сохраняем задачу и проверяем её отображение
    private fun saveAndCheckCreatedTaskTest() {
        Espresso.onView(ViewMatchers.withContentDescription(R.string.action_save_task))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btn_add))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(taskName))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}