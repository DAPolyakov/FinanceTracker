package io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.configurator.ScheduledTransactionsConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.presenter.IScheduledTransactionsInput
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.presenter.ScheduledTransactionsPresenter
import io.alekseimartoyas.tradetracker.Foundation.BaseFragment
import kotlinx.android.synthetic.main.fragment_history.*

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

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter?.onStop()
    }
}