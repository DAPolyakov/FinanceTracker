package io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.configurator.ScheduledTransactionsConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.presenter.IScheduledTransactionsInput
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.presenter.ScheduledTransactionsPresenter
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.view.RecyclerViewManager.ScheduledTransactionsRVAdapter
import io.alekseimartoyas.tradetracker.Foundation.BaseFragment
import kotlinx.android.synthetic.main.fragment_scheduled_transactions.*

class ScheduledTransactionsFragment : BaseFragment<ScheduledTransactionsPresenter>(),
        IScheduledTransactionsInput {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scheduled_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ScheduledTransactionsConfigurator().buildModule(this)

        transaction_rv.layoutManager = LinearLayoutManager(context)
        transaction_rv.adapter = presenter!!.getAdapter()
    }

    override fun showTransactions(data: List<FinanceTransaction>) {
        empty_state?.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE
        (transaction_rv?.adapter as? ScheduledTransactionsRVAdapter)?.setData(data.toTypedArray())
    }

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter?.onStop()
    }
}