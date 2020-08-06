package com.example.kotlinstudy.activity

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.kotlinstudy.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleListActivityTest {

    @Test
    fun testUI() {
        Espresso.onView(withId(R.id.text_title)).perform(click())
    }
}