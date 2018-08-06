package io.alekseimartoyas.financetracker.ui

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.view.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
open class SwitchAccountTest {

    @Rule
    @JvmField
    val mainActivityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    @Throws(Exception::class)
    fun intentInit() {
        Intents.init()
    }

    @After
    @Throws(Exception::class)
    fun intentRelease() {
        Intents.release()
    }

    @Test
    @Throws(Exception::class)
    fun switchAccountUiTest() {

        Espresso.onView(ViewMatchers.withId(R.id.main_quant_text)).check(ViewAssertions.matches(ViewMatchers.withText("877.0")))
        Espresso.onView(ViewMatchers.withId(R.id.spinner_account)).perform(ViewActions.click())
        Espresso.onView(CoreMatchers
                .allOf(ViewMatchers.withId(android.R.id.text1), ViewMatchers.withText(InstrumentationRegistry.getTargetContext().getString(R.string.maria))))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.main_quant_text)).check(ViewAssertions.matches(ViewMatchers.withText("200")))
    }

}