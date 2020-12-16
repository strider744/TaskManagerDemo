package com.strider.taskmanager

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.strider.taskmanager.ui.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule
import kotlin.random.Random

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class T13SwipeRightToDeleteTaskTest {

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
    fun t13SwipeRightToDeleteTaskTest() {
        createTask()
        saveAndCheckCreatedTaskTest()

        onView(withText(taskName)).perform(swipeRight())
        Thread.sleep(2000)
        onView(withText(taskName)).check(doesNotExist())
        onView(withText(R.string.undo_upper_text)).check(matches(isDisplayed())).perform(click())
        Thread.sleep(500)
        onView(withText(taskName)).check(matches(isDisplayed()))
    }

    // создание задачи
    private fun createTask() {
        onView(withId(R.id.btn_add)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.et_task_title)).check(matches(isDisplayed())).perform(typeText(taskName))
        onView(withId(R.id.et_task_description)).check(matches(isDisplayed()))
            .perform(typeText(taskDescription))
    }

    // сохраняем задачу и проверяем её отображение
    private fun saveAndCheckCreatedTaskTest() {
        onView(withContentDescription(R.string.action_save_task)).perform(click())
        onView(withId(R.id.btn_add)).check(matches(isDisplayed()))
        onView(withText(taskName)).check(matches(isDisplayed()))
    }
}