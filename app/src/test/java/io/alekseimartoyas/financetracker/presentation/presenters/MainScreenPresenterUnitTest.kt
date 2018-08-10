package io.alekseimartoyas.financetracker.presentation.presenters

import io.alekseimartoyas.financetracker.base.BaseUnitTest
import io.alekseimartoyas.financetracker.base.any
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.data.network.DailyResponse
import io.alekseimartoyas.financetracker.data.network.Information
import io.alekseimartoyas.financetracker.data.network.Values
import io.alekseimartoyas.financetracker.data.services.DataSource
import io.alekseimartoyas.financetracker.data.services.ExchRateProvider
import io.alekseimartoyas.financetracker.domain.CategoryType.Education
import io.alekseimartoyas.financetracker.domain.Currency.RUB
import io.alekseimartoyas.financetracker.domain.Currency.USD
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState.Done
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState.Waiting
import io.alekseimartoyas.financetracker.domain.OperationType.DEBIT
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetExchRateInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetNewTransactionsFromScheduledInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetTransactionsByAccountIdInteractor
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.MainScreenPresenter
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.MainScreenFragment
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.never
import java.math.BigDecimal


class MainScreenPresenterUnitTest : BaseUnitTest() {

    lateinit var presenter: MainScreenPresenter

    lateinit var getAccountsInteractor: GetAccountsInteractor
    lateinit var getExchRateInteractor: GetExchRateInteractor
    lateinit var getNewTransactionsFromScheduledInteractor: GetNewTransactionsFromScheduledInteractor
    lateinit var getTransactionsByAccountIdInteractor: GetTransactionsByAccountIdInteractor

    @Mock
    lateinit var view: MainScreenFragment
    @Mock
    lateinit var router: IMainActivityRouterInput
    @Mock
    lateinit var dataSource: DataSource
    @Mock
    lateinit var exchRateProvider: ExchRateProvider

    private val account = Account("Wallet_1", RUB, BigDecimal(15), 1)
    private val listAccount = listOf(
            Account("Wallet_1", RUB, BigDecimal(15), 1),
            Account("Wallet_2", USD, BigDecimal(0), 2)
    )

    private val transaction = FinanceTransaction(
            operationType = DEBIT,
            quantity = 1f,
            currency = USD,
            category = Education,
            date = "12.10.2010",
            timeStart = 1,
            timeFinish = 1,
            accountId = 2,
            state = Done,
            id = 1
    )

    private val listTransactions = listOf(
            transaction.copy(state = Done, id = 2),
            transaction.copy(state = Waiting, id = 3),
            transaction.copy(state = Done, id = 4)
    )

    private val listScheduledTransactions = listOf(
            transaction.copy(state = Waiting, id = 2),
            transaction.copy(state = Waiting, id = 3),
            transaction.copy(state = Waiting, id = 4)
    )

    private val daylyRespone = DailyResponse(Values(
            USD = Information(53.1, "USD"),
            AUD = Information(43.1, "AUD"),
            EUR = Information(33.1, "EUR"),
            GBP = Information(23.1, "GBR")
    ))

    @Before
    override fun onInit() {
        super.onInit()

        presenter = MainScreenPresenter(
                view,
                getAccountsInteractor,
                getExchRateInteractor,
                getNewTransactionsFromScheduledInteractor,
                getTransactionsByAccountIdInteractor,
                router
        )
    }

    override fun onMockInit() {
        getAccountsInteractor = GetAccountsInteractor(dataSource, testScheduler, testScheduler)
        getExchRateInteractor = GetExchRateInteractor(exchRateProvider, testScheduler, testScheduler)
        getNewTransactionsFromScheduledInteractor = GetNewTransactionsFromScheduledInteractor(dataSource, testScheduler, testScheduler)
        getTransactionsByAccountIdInteractor = GetTransactionsByAccountIdInteractor(dataSource, testScheduler, testScheduler)
    }

    @Test
    fun onStart_showBalance_success() {
        doReturn(Flowable.just(listAccount)).`when`(dataSource).getAccounts()
        presenter.onStart()
        testScheduler.triggerActions()

        Mockito.verify(view).showAccountsList(any())
        Mockito.verify(view).showBalance(any())
    }

    @Test
    fun onStart_showBalance_error() {
        doReturn(Flowable.just(emptyList<Account>())).`when`(dataSource).getAccounts()
        presenter.onStart()
        testScheduler.triggerActions()

        Mockito.verify(view).showAccountsList(any())
        Mockito.verify(view, never()).showBalance(any())
    }

    @Test
    fun onStart_getExchRate_success() {
        doReturn(Flowable.just(listAccount)).`when`(dataSource).getAccounts()
        doReturn(Observable.just(daylyRespone)).`when`(exchRateProvider).getExchRates()

        presenter.onStart()
        testScheduler.triggerActions()
        testScheduler.triggerActions()

        Mockito.verify(view).setExchRate(any())
    }

    @Test
    fun showAddTransaction() {
        presenter.showAddTransaction()
        Mockito.verify(router, Mockito.times(1)).showAddTransaction()
    }

    @Test
    fun checkScheduledTransactions_success() {
        doReturn(Flowable.just(listScheduledTransactions)).`when`(dataSource).getNewTransactionsFromScheduled()

        presenter.checkScheduledTransactions()
        testScheduler.triggerActions()
        Mockito.verify(router, Mockito.times(1)).showAddTransaction(any())
    }

    @Test
    fun changeCurrentAccount_success() {
        doReturn(Flowable.just(listTransactions)).`when`(dataSource).getTransactionsByAccountId(1)

        presenter.changeCurrentAccount(account)

        Mockito.verify(view).showBalance(account)
        Mockito.verify(view).showBalance(1.0, account)

        testScheduler.triggerActions()
        Mockito.verify(view).changePieChartData(any())
    }

    @Test
    fun getParameters() {
        presenter.getAccounts
        presenter.getExchRateInteractor
        presenter.getNewTransactionsFromScheduledInteractor
        presenter.getTransactionsByAccountIdInteractor
    }

    @Test
    fun onStop() {
        presenter.onStop()
    }

    @Test
    fun destructor() {
        presenter.destructor()
    }
}