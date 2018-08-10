package io.alekseimartoyas.financetracker.ui

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.DrawerActions
import android.support.test.espresso.contrib.DrawerMatchers
import android.support.test.espresso.contrib.NavigationViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.Gravity
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.view.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class NavigationTest {

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
    fun navigateToHistory() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())

        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_history))

        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .perform(DrawerActions.close())

        Espresso.onView(ViewMatchers.withId(R.id.toolbar_main_activity))
                .check(ViewAssertions.matches(hasDescendant(ViewMatchers.withText(InstrumentationRegistry.getTargetContext().getString(R.string.nav_history)))))

    }

    @Test
    @Throws(Exception::class)
    fun navigateToScheduledTransactions() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())

        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_scheduled_transactions))

        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .perform(DrawerActions.close())

        Espresso.onView(ViewMatchers.withId(R.id.toolbar_main_activity))
                .check(ViewAssertions.matches(hasDescendant(ViewMatchers.withText(InstrumentationRegistry.getTargetContext().getString(R.string.nav_scheduled_transactions)))))

    }


    @Test
    @Throws(Exception::class)
    fun navigateToTemplates() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())

        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_templates))

        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .perform(DrawerActions.close())

        Espresso.onView(ViewMatchers.withId(R.id.toolbar_main_activity))
                .check(ViewAssertions.matches(hasDescendant(ViewMatchers.withText(InstrumentationRegistry.getTargetContext().getString(R.string.nav_templates)))))

    }

    @Test
    @Throws(Exception::class)
    fun navigateToStatistics() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())

        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_statistics))

        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .perform(DrawerActions.close())

        Espresso.onView(ViewMatchers.withId(R.id.toolbar_main_activity))
                .check(ViewAssertions.matches(hasDescendant(ViewMatchers.withText(InstrumentationRegistry.getTargetContext().getString(R.string.nav_statistics)))))

    }

    @Test
    @Throws(Exception::class)
    fun navigateToBalance() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())

        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_main))

        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .perform(DrawerActions.close())

        Espresso.onView(ViewMatchers.withId(R.id.toolbar_main_activity))
                .check(ViewAssertions.matches(hasDescendant(ViewMatchers.withText(InstrumentationRegistry.getTargetContext().getString(R.string.nav_main)))))

    }


    @Test
    @Throws(Exception::class)
    fun navigateToSettings() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())

        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_settings))

        Espresso.onView(ViewMatchers.withId(R.id.toolbar_settings_activity))
                .check(ViewAssertions.matches(hasDescendant(ViewMatchers.withText(InstrumentationRegistry.getTargetContext().getString(R.string.nav_settings)))))

    }


}