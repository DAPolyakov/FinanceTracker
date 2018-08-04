package io.alekseimartoyas.financetracker.presentation.modules.addtransaction.view

import android.os.Bundle
import android.support.v7.widget.Toolbar
import io.alekseimartoyas.financetracker.BuildConfig
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.configurator.AddTransactionConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.presenter.AddTransactionPresenter
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.presenter.IAddTransactionActivityInput
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.view.spinnermanager.CategorySpinnerArrayAdapter
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.SpinnerManager.AccountSpinnerArrayAdapter
import io.alekseimartoyas.financetracker.utils.daysToMillis
import io.alekseimartoyas.financetracker.utils.millisToDays
import io.alekseimartoyas.financetracker.utils.millisToSeconds
import io.alekseimartoyas.financetracker.utils.secondsToMillis
import io.alekseimartoyas.tradetracker.Foundation.BaseActivity
import kotlinx.android.synthetic.main.activity_add_transaction.*

class AddTransactionActivity : BaseActivity<AddTransactionPresenter>(),
        IAddTransactionActivityInput {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        configurationToolbar()

        AddTransactionConfigurator().buildModule(this)

        initSpinners()

        add_transaction_bt.setOnClickListener {

            val days = input_repeat_days.text.toString()
            val repeatDays = if (days.isBlank()) 0 else days.toInt()
            val state: FinanceTransactionState
            val timeStart = System.currentTimeMillis()

            val timeFinish = timeStart + if (BuildConfig.DEBUG) {
                repeatDays.secondsToMillis()
            } else {
                repeatDays.daysToMillis()
            }

            state = if (repeatDays > 0) FinanceTransactionState.Waiting else FinanceTransactionState.Done

            val operationType = when (operation_type_spinner.selectedItem) {
                "Enlistment" -> OperationType.ENLISTMENT
                else -> OperationType.DEBIT
            }

            val currency = when (quantity_currency_spinner.selectedItem) {
                "RUB" -> Currency.RUB
                else -> Currency.USD
            }

            presenter?.onAddFinanceTransaction(FinanceTransaction(
                    operationType = operationType,
                    quantity = quantity_edit.text.toString().toFloat(),
                    currency = currency,
                    accountId = (spinner_account.selectedItem as Account).id!!,
                    category = (spinner_category.selectedItem as CategoryType),
                    date = presenter!!.getDate(),
                    state = state,
                    timeStart = timeStart,
                    timeFinish = timeFinish
            ), (spinner_account.selectedItem as Account).currency)
        }

        cancel_transaction_bt.setOnClickListener {
            presenter?.cancelTransaction(intent.getParcelableExtra("transaction"))
        }
    }

    override fun loadTransaction() {
        loadFields(intent.getParcelableExtra("transaction"))
    }

    private fun loadFields(transaction: FinanceTransaction?) {
        transaction?.apply {

            input_repeat_days.setText(getRepeatDays(timeFinish - timeStart).toString())
            quantity_edit.setText(quantity.toString())
            operation_type_spinner.setSelection(getOperationTypeSpinnerPosition(operationType))
            quantity_currency_spinner.setSelection(getCurrencySpinnerPosition(currency))
            spinner_category.setSelection(getCategorySpinnerPosition(category))
            spinner_account.setSelection(getAccountSpinnerPosition(accountId))

            presenter?.loadTransactionId(id)
        }
    }

    private fun getOperationTypeSpinnerPosition(operationType: OperationType): Int {
        for (i in 0 until operation_type_spinner.adapter.count) {
            val item = operation_type_spinner.getItemAtPosition(i) as String
            if (operationType.name.equals(item, true)) {
                return i
            }
        }
        return 0
    }

    private fun getCurrencySpinnerPosition(currency: Currency): Int {
        for (i in 0 until quantity_currency_spinner.adapter.count) {
            val item = quantity_currency_spinner.getItemAtPosition(i) as String
            if (currency.name.equals(item, true)) {
                return i
            }
        }
        return 0
    }

    private fun getCategorySpinnerPosition(category: CategoryType): Int {
        for (i in 0 until spinner_category.adapter.count) {
            val item = spinner_category.getItemAtPosition(i) as CategoryType
            if (category.name.equals(item.name, true)) {
                return i
            }
        }
        return 0
    }

    private fun getAccountSpinnerPosition(accountId: Long): Int {
        for (i in 0 until spinner_account.adapter.count) {
            val item = spinner_account.getItemAtPosition(i) as Account
            if (accountId == item.id) {
                return i
            }
        }
        return 0
    }

    private fun getRepeatDays(millis: Long): Long {
        return if (BuildConfig.DEBUG) {
            millis.millisToSeconds()
        } else {
            millis.millisToDays()
        }
    }

    override fun back() {
        finish()
    }

    private fun initSpinners() {
        spinner_category?.adapter = CategorySpinnerArrayAdapter(this, CategoryType.values())
    }

    override fun setAccountsList(accounts: Array<Account>) {
        spinner_account?.adapter = AccountSpinnerArrayAdapter(this, accounts)
    }

    private fun configurationToolbar() {
        setSupportActionBar(toolbar_add_transaction_activity as Toolbar)
        title = resources.getString(R.string.add_button)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
    }

    override fun onStop() {
        presenter?.onStop()
        super.onStop()
    }
}
