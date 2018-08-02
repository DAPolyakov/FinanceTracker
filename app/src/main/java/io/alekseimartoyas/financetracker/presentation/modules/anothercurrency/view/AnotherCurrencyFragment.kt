package io.alekseimartoyas.financetracker.presentation.modules.anothercurrency.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.presentation.modules.anothercurrency.configurator.AnotherCurrencyConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.anothercurrency.presenter.AnotherCurrencyPresenter
import io.alekseimartoyas.financetracker.presentation.modules.anothercurrency.presenter.IAnotherCurrencyFragmentInput
import io.alekseimartoyas.financetracker.utils.toTargetCurrency
import io.alekseimartoyas.tradetracker.Foundation.BaseFragment
import kotlinx.android.synthetic.main.fragment_another_currency.*

class AnotherCurrencyFragment : BaseFragment<AnotherCurrencyPresenter>(),
        IAnotherCurrencyFragmentInput {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_another_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnotherCurrencyConfigurator().buildModule(this)
    }

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
    }

    override fun setExchRate(data: String) {
        //переделать отношение
        //относительно рубля или чего либо
        chang_rate_usd.text = "$data ${resources.getString(R.string.RUB)}"
    }


    override fun showBalance(course: Double, account: Account) {
        val s = account.currency.toTargetCurrency(Currency.USD, account.amount, course.toBigDecimal()).toString()
        usd_curr_chang_text.text = s
    }

    override fun onStop() {
        super.onStop()
        presenter?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.destructor()
    }
}