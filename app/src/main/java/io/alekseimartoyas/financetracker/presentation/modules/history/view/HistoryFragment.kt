package io.alekseimartoyas.financetracker.presentation.modules.history.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.presentation.modules.history.configurator.HistoryConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.history.presenter.HistoryPresenter
import io.alekseimartoyas.financetracker.presentation.modules.history.presenter.IHistoryFragmentInput
import io.alekseimartoyas.financetracker.presentation.modules.history.view.RecyclerViewManager.TransactionRVAdapter
import io.alekseimartoyas.tradetracker.Foundation.BaseFragment
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : BaseFragment<HistoryPresenter>(),
        IHistoryFragmentInput {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HistoryConfigurator().buildModule(this)
        transaction_rv.layoutManager = LinearLayoutManager(layoutInflater.context,
                LinearLayout.VERTICAL,
                false)
        transaction_rv.adapter = presenter!!.getAdapter()
    }

    override fun showTransactions(data: List<FinanceTransaction>) {
        empty_state.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE
        (transaction_rv.adapter as TransactionRVAdapter).setData(data.toTypedArray())
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