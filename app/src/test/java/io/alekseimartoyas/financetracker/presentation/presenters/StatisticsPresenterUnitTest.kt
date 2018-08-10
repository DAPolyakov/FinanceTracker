package io.alekseimartoyas.financetracker.presentation.presenters

import android.content.res.Resources
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.base.BaseUnitTest
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.data.services.DataSource
import io.alekseimartoyas.financetracker.domain.*
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetTransactionsInteractor
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.financetracker.presentation.modules.statistics.presenter.StatisticsPresenter
import io.alekseimartoyas.financetracker.presentation.modules.statistics.view.StatisticsFragment
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import java.math.BigDecimal


class StatisticsPresenterUnitTest : BaseUnitTest() {

    lateinit var presenter: StatisticsPresenter

    lateinit var getTransactionsInteractor: GetTransactionsInteractor
    lateinit var getAccountsInteractor: GetAccountsInteractor

    @Mock
    lateinit var view: StatisticsFragment
    @Mock
    lateinit var router: IMainActivityRouterInput
    @Mock
    lateinit var dataSource: DataSource
    @Mock
    lateinit var res: Resources

    private val transaction = FinanceTransaction(
            operationType = OperationType.DEBIT,
            quantity = 1f,
            currency = Currency.USD,
            category = CategoryType.Salary,
            date = "12.10.2010",
            timeStart = 1,
            timeFinish = 1,
            accountId = 2,
            state = FinanceTransactionState.Done,
            id = 1
    )


    private val account = Account("Wallet_1", Currency.RUB, BigDecimal(15), 1)
    private val listAccount = listOf(
            Account("Wallet_1", Currency.RUB, BigDecimal(15), 1),
            Account("Wallet_2", Currency.USD, BigDecimal(0), 2)
    )

    private val listTransactions = listOf(
            transaction.copy(state = FinanceTransactionState.Done, id = 2),
            transaction.copy(state = FinanceTransactionState.Done, id = 3),
            transaction.copy(state = FinanceTransactionState.Done, id = 4)
    )

    @Before
    override fun onInit() {
        super.onInit()
        presenter = StatisticsPresenter(
                view,
                getTransactionsInteractor,
                getAccountsInteractor,
                router
        )
    }

    override fun onMockInit() {
        getTransactionsInteractor = GetTransactionsInteractor(dataSource, testScheduler, testScheduler)
        getAccountsInteractor = GetAccountsInteractor(dataSource, testScheduler, testScheduler)
    }

    @Test
    fun onStart() {
        doReturn(Flowable.just(listAccount)).`when`(dataSource).getAccounts()
        doReturn(Flowable.just(listTransactions)).`when`(dataSource).getTransactions()
        presenter.onStart()

        testScheduler.triggerActions()
        verify(view).setAccountsList(ArrayList(listAccount))
        verify(view).showStatistics(listTransactions)
    }

    @Test
    fun setFilter() {
        doReturn(Flowable.just(listTransactions)).`when`(dataSource).getTransactions()
        presenter.onStart()
        testScheduler.triggerActions()

        presenter.setFilter(
                type = "Debit",
                category = StatisticsCategoryType.Salary,
                account = account,
                dateStart = "01-01-2010",
                dateFinish = "10-02-2011",
                res = res)

        testScheduler.triggerActions()
        verify(view).showStatistics(listTransactions)
    }

    @Test
    fun onStop() {
        presenter.onStop()
    }

}