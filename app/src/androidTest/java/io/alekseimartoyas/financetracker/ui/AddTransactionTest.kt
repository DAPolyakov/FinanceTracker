package io.alekseimartoyas.financetracker.ui

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.DrawerActions
import android.support.test.espresso.contrib.DrawerMatchers.isClosed
import android.support.test.espresso.contrib.NavigationViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import android.view.Gravity
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.view.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
open class AddTransactionTest {

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            InstrumentationRegistry.getTargetContext().deleteDatabase("finance_tracker")
        }
    }

    @Rule
    @JvmField
    val mainActivityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    private lateinit var context: Context

    @Before
    @Throws(Exception::class)
    fun intentInit() {
        Intents.init()
        context = InstrumentationRegistry.getTargetContext()
    }

    @After
    @Throws(Exception::class)
    fun intentRelease() {
        Intents.release()
    }

    @Test
    @Throws(Exception::class)
    fun addTransactionUiTest() {

        onView(withId(R.id.main_quant_text)).check(matches(withText("300,00")))

        onView(withId(R.id.add_transaction_fab)).perform(click())

        onView(withId(R.id.quantity_edit)).perform(typeText("777"), closeSoftKeyboard())

        onView(withId(R.id.add_transaction_bt)).perform(click())

        onView(withId(R.id.main_quant_text)).check(matches(withText("1077,00")))

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_history))

        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.close())


        onView(allOf(withId(R.id.transaction_rv), isDisplayed()))
        onView(withId(R.id.transaction_rv)).check(matches(hasDescendant(withText(InstrumentationRegistry.getTargetContext().getString(R.string.salary)))))
        onView(withId(R.id.transaction_rv)).check(matches(hasDescendant(withText("+ 777,00 ${context.getString(Currency.RUB.strId)}"))))
    }

}