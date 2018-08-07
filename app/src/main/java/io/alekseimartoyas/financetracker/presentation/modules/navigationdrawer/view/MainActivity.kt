package io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.view

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.presentation.modules.addaccount.view.AddAccountFragment
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.view.AddTransactionActivity
import io.alekseimartoyas.financetracker.presentation.modules.history.view.HistoryFragment
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.AccountListFragment
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.MainScreenFragment
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.presenter.IMainActivityInput
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.presenter.MainActivityPresenter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.view.ScheduledTransactionsFragment
import io.alekseimartoyas.financetracker.presentation.modules.settings.view.SettingsActivity
import io.alekseimartoyas.financetracker.utils.isTabledMode
import io.alekseimartoyas.tradetracker.Foundation.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainActivityPresenter>(),
        IMainActivityInput,
        NavigationView.OnNavigationItemSelectedListener,
        IMainActivityRouterInput {

    var mainFragment = 0
    var secondFragment: Int = 0
    private val keyCurrentFragment = "mainFragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        nav_view.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            mainFragment = R.id.nav_main
            nav_view.setCheckedItem(R.id.nav_main)
            replaceMainFragment(MainScreenFragment())
            if (isTabledMode()) {
//                replaceMainFragment(AccountListFragment())
            }
        } else {
            mainFragment = savedInstanceState.getInt(keyCurrentFragment)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar_main_activity as Toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar_main_activity as Toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId != mainFragment) {
            when (item.itemId) {
                R.id.nav_main -> {
                    mainFragment = R.id.nav_main
                    replaceMainFragment(MainScreenFragment())
                }
                R.id.nav_history -> {
                    mainFragment = R.id.nav_history
                    replaceMainFragment(HistoryFragment())
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
                R.id.nav_scheduled_transactions -> {
                    mainFragment = R.id.nav_scheduled_transactions
                    replaceMainFragment(ScheduledTransactionsFragment())
                }
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceMainFragment(fragment: Fragment) {

        supportActionBar?.setTitle(when (fragment) {
            is MainScreenFragment -> R.string.nav_main
            is HistoryFragment -> R.string.nav_history
            is ScheduledTransactionsFragment -> R.string.nav_scheduled_transactions
            else -> R.string.app_name
        })

        supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, fragment, "visible_fragment")
                .commit()
    }

    private fun replaceSecondFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.second_frame, fragment)
                .commit()
    }


    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState?.putInt(keyCurrentFragment, mainFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destructor()
        presenter = null
    }

    override fun showSettings() {
        this.startActivity(Intent(this, SettingsActivity::class.java))
    }

    override fun showAddTransaction(financeTransaction: FinanceTransaction?) {
        this.startActivity(Intent(this, AddTransactionActivity::class.java)
                .putExtra("transaction", financeTransaction))
    }

    override fun showAddAccount() {
        supportFragmentManager  //вынести
                .beginTransaction()
                .replace(R.id.main_frame, AddAccountFragment(), "visible_fragment")
                .addToBackStack(null)
                .commit()
    }

    override fun returnFromAddAccount() {
        supportFragmentManager.beginTransaction()
                .remove(supportFragmentManager.findFragmentByTag("visible_fragment")!!)
                .commit()
    }
}
