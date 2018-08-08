package io.alekseimartoyas.financetracker.presentation.modules.templates.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.presentation.modules.templates.configurator.TemplatesConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.templates.presenter.TemplatesPresenter
import io.alekseimartoyas.tradetracker.Foundation.BaseFragment
import kotlinx.android.synthetic.main.fragment_history.*


class TemplatesFragment : BaseFragment<TemplatesPresenter>(),
        TemplatesView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scheduled_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TemplatesConfigurator().buildModule(this)

        transaction_rv.layoutManager = LinearLayoutManager(context)
    }

    override fun setRvAdapter(adapter: TemplatesRvAdapter) {
        transaction_rv.adapter = adapter
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