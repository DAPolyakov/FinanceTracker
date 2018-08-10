package io.alekseimartoyas.financetracker.presentation.presenters

import io.alekseimartoyas.financetracker.base.BaseUnitTest
import io.alekseimartoyas.financetracker.base.any
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.data.services.DataSource
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.domain.interactors.GetScheduledTransactionsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.UpdateFinanceTransactionInteractor
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.configurator.MainScreenConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.configurator.ScheduledTransactionsConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.presenter.IScheduledTransactionsInput
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.presenter.ScheduledTransactionsPresenter
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.view.RecyclerViewManager.ScheduledTransactionsRVAdapter
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.view.ScheduledTransactionsFragment
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn

class ScheduledTransactionsPresenterUnitTest : BaseUnitTest() {

    lateinit var presenter: ScheduledTransactionsPresenter

    @Mock
    lateinit var view: ScheduledTransactionsFragment

    lateinit var getScheduledTransactionsInteractor: GetScheduledTransactionsInteractor
    lateinit var updateFinanceTransactionInteractor: UpdateFinanceTransactionInteractor

    @Mock
    lateinit var router: IMainActivityRouterInput
    @Mock
    lateinit var dataSource: DataSource
    @Mock
    lateinit var rvAdapter: ScheduledTransactionsRVAdapter

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

    private val listScheduledTransactions = listOf(
            transaction.copy(state = FinanceTransactionState.Waiting, id = 2),
            transaction.copy(state = FinanceTransactionState.Waiting, id = 3),
            transaction.copy(state = FinanceTransactionState.Waiting, id = 4)
    )

    @Before
    override fun onInit() {
        super.onInit()
        presenter = ScheduledTransactionsPresenter(
                view,
                getScheduledTransactionsInteractor,
                updateFinanceTransactionInteractor,
                router,
                rvAdapter
        )
    }

    override fun onMockInit() {
        getScheduledTransactionsInteractor = GetScheduledTransactionsInteractor(dataSource, testScheduler, testScheduler)
        updateFinanceTransactionInteractor = UpdateFinanceTransactionInteractor(dataSource, testScheduler, testScheduler)
    }

    @Test
    fun onStart() {
        doReturn(Flowable.just(listScheduledTransactions)).`when`(dataSource).getScheduledTransactions()
        presenter.onStart()
        testScheduler.triggerActions()
        Mockito.verify(view).showTransactions(any())
    }

    @Test
    fun getAdapter() {
        presenter.getAdapter()
    }

    @Test
    fun onStop() {
        presenter.onStop()
    }

}