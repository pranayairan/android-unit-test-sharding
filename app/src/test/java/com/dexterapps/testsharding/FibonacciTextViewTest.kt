package com.dexterapps.testsharding

import android.app.Activity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@Category(RobolectricTests::class)
@RunWith(RobolectricTestRunner::class)
class FibonacciTextViewTest {

    private lateinit var activityController: ActivityController<Activity>
    private lateinit var activity: Activity

    private lateinit var fibonacciTextView: FibonacciTextView

    @Before
    fun setUp() {
        // Create an activity (Can be any sub-class: i.e. AppCompatActivity, FragmentActivity, etc)
        activityController = Robolectric.buildActivity(Activity::class.java)
        activity = activityController.get()

        // Create the view using the activity context
        fibonacciTextView = FibonacciTextView(activity)
    }

    @Test
    fun `should display 1 by default`() {
        assertEquals("1", fibonacciTextView.text)
    }

    @Test
    fun `clicking the text increments the fibonacci sequence`() {
        val expected = arrayOf(2, 3, 5, 8, 13, 21, 34, 55, 89, 144)
        expected.forEach {
            fibonacciTextView.performClick()
            assertEquals("$it", fibonacciTextView.text)
        }
    }
}
