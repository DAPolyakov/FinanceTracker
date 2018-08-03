package io.alekseimartoyas.financetracker.presentation.modules.addtransaction.view

import android.os.Bundle
import android.support.v7.widget.Toolbar
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.configurator.AddTransactionConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.presenter.AddTransactionPresenter
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.presenter.IAddTransactionActivityInput
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.view.spinnermanager.CategorySpinnerArrayAdapter
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.SpinnerManager.AccountSpinnerArrayAdapter
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
                    date = presenter!!.getDate()
            ))
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
