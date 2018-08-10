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
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.domain.interactors.*
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.presenter.AddTransactionPresenter
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.view.AddTransactionActivity
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.math.BigDecimal

class AddTransactionPresenterUnitTest : BaseUnitTest() {

    lateinit var presenter: AddTransactionPresenter

    lateinit var updateFinanceTransactionInteractor: UpdateFinanceTransactionInteractor
    lateinit var getExchRateInteractor: GetExchRateInteractor
    lateinit var addFinanceTransactionInteractor: AddFinanceTransactionInteractor
    lateinit var getAccountsInteractor: GetAccountsInteractor
    lateinit var getTemplateTransactionsInteractor: GetTemplateTransactionsInteractor

    @Mock
    lateinit var view: AddTransactionActivity
    @Mock
    lateinit var dataSource: DataSource
    @Mock
    lateinit var exchRateProvider: ExchRateProvider

    private val listAccount = listOf(
            Account("Wallet_1", Currency.RUB, BigDecimal(15), 1),
            Account("Wallet_2", Currency.USD, BigDecimal(0), 2)
    )

    private val transaction = FinanceTransaction(
            operationType = OperationType.DEBIT,
            quantity = 1f,
            currency = Currency.USD,
            category = CategoryType.Education,
            date = "12.10.2010",
            timeStart = 1,
            timeFinish = 1,
            accountId = 2,
            state = FinanceTransactionState.Done,
            id = 1
    )

    private val listTemplateTransactions = listOf(
            transaction.copy(state = FinanceTransactionState.Template, id = 2),
            transaction.copy(state = FinanceTransactionState.Template, id = 3),
            transaction.copy(state = FinanceTransactionState.Template, id = 4)
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

        presenter = AddTransactionPresenter(
                view,
                updateFinanceTransactionInteractor,
                getExchRateInteractor,
                addFinanceTransactionInteractor,
                getAccountsInteractor,
                getTemplateTransactionsInteractor
        )
    }

    override fun onMockInit() {
        getAccountsInteractor = GetAccountsInteractor(dataSource, testScheduler, testScheduler)
        getExchRateInteractor = GetExchRateInteractor(exchRateProvider, testScheduler, testScheduler)
        updateFinanceTransactionInteractor = UpdateFinanceTransactionInteractor(dataSource, testScheduler, testScheduler)
        addFinanceTransactionInteractor = AddFinanceTransactionInteractor(dataSource, testScheduler, testScheduler)
        getTemplateTransactionsInteractor = GetTemplateTransactionsInteractor(dataSource, testScheduler, testScheduler)
    }

    @Test
    fun onStart_onePaneMode() {
        doReturn(Flowable.just(listAccount)).`when`(dataSource).getAccounts()
        doReturn(Flowable.just(listTemplateTransactions)).`when`(dataSource).getTemplateTransactions()

        presenter.setTabletMode(false)
        presenter.onStart()
        testScheduler.triggerActions()

        Mockito.verify(view).setAccountsList(any())
        Mockito.verify(view).loadTransaction()

        Mockito.verify(view, never()).showTemplates(any())
    }

    @Test
    fun onStart_twoPaneMode() {
        doReturn(Flowable.just(listAccount)).`when`(dataSource).getAccounts()
        doReturn(Flowable.just(listTemplateTransactions)).`when`(dataSource).getTemplateTransactions()

        presenter.setTabletMode(true)
        presenter.onStart()
        testScheduler.triggerActions()

        Mockito.verify(view).setAccountsList(any())
        Mockito.verify(view).loadTransaction()

        testScheduler.triggerActions()
        Mockito.verify(view).showTemplates(any())
    }

    @Test
    fun onAddFinanceTransaction_isTemplate_noneId() {
        doReturn(Observable.just(daylyRespone)).`when`(exchRateProvider).getExchRates()
        doReturn(Flowable.just(Unit)).`when`(dataSource).addTransaction(any(), any())

        presenter.onAddFinanceTransaction(transaction, Currency.RUB, true)
        testScheduler.triggerActions()

        Mockito.verify(view, times(1)).back()
    }

    @Test
    fun onAddFinanceTransaction_noTemplate_withId() {
        doReturn(Observable.just(daylyRespone)).`when`(exchRateProvider).getExchRates()
        doReturn(Flowable.just(Unit)).`when`(dataSource).updateFinanceTransaction(any(), any())

        presenter.loadTransactionId(12)
        presenter.onAddFinanceTransaction(transaction, Currency.RUB, false)
        testScheduler.triggerActions()
        Mockito.verify(view, times(1)).back()
    }

    @Test
    fun getDate() {
        presenter.getDate().isNotBlank()
    }

    @Test
    fun cancelTransaction_withTransactionTemplate() {
        presenter.cancelTransaction(transaction.copy(state = FinanceTransactionState.Template))
        Mockito.verify(view, times(1)).back()
    }

    @Test
    fun cancelTransaction_withTransactionNonTemplate() {
        doReturn(Flowable.just(Unit)).`when`(dataSource).updateFinanceTransaction(any(), any())
        presenter.cancelTransaction(transaction.copy(state = FinanceTransactionState.Done))
        testScheduler.triggerActions()
        Mockito.verify(view, times(1)).back()
    }

    @Test
    fun cancelTransaction_noneTransaction() {
        presenter.cancelTransaction(null)
        Mockito.verify(view, times(1)).back()
    }

    @Test
    fun onDeleteTemplate() {
        doReturn(Flowable.just(Unit)).`when`(dataSource).updateFinanceTransaction(any(), any())
        presenter.onDeleteTemplate(transaction)
        testScheduler.triggerActions()
    }

    @Test
    fun onStop() {
        presenter.onStop()
    }

}