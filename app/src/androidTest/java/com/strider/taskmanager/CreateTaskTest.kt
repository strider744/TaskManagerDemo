package com.strider.taskmanager

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
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
class CreateTaskTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var taskName: String

    @Before
    fun before() {
        taskName = Random.nextInt().toString()
    }

    @Test
    fun useAppContext() {
        onView(withId(R.id.btn_add)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_add)).perform(click())
        onView(withId(R.id.et_task_title)).check(matches(isDisplayed()))
        onView(withId(R.id.et_task_title)).perform(typeText(taskName))
        onView(withContentDescription(R.string.action_save_task)).perform(click())
        onView(withId(R.id.btn_add)).check(matches(isDisplayed()))
        onView(withText(taskName)).check(matches(isDisplayed()))
    }
}