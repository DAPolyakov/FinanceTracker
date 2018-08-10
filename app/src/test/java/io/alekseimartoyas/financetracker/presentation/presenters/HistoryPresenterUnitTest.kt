package io.alekseimartoyas.financetracker.presentation.presenters

import io.alekseimartoyas.financetracker.base.BaseUnitTest
import io.alekseimartoyas.financetracker.base.any
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.data.services.DataSource
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.domain.interactors.GetDoneTransactionsInteractor
import io.alekseimartoyas.financetracker.presentation.modules.history.presenter.HistoryPresenter
import io.alekseimartoyas.financetracker.presentation.modules.history.presenter.ITransactionRVInput
import io.alekseimartoyas.financetracker.presentation.modules.history.view.HistoryFragment
import io.alekseimartoyas.financetracker.presentation.modules.history.view.RecyclerViewManager.TransactionRVAdapter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn


class HistoryPresenterUnitTest : BaseUnitTest() {

    lateinit var presenter: HistoryPresenter

    lateinit var getDoneTransactionsInteractor: GetDoneTransactionsInteractor

    @Mock
    lateinit var view: HistoryFragment
    @Mock
    lateinit var router: IMainActivityRouterInput
    @Mock
    lateinit var dataSource: DataSource
    @Mock
    lateinit var rvAdapter: TransactionRVAdapter

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

    private val listDoneTransactions = listOf(
            transaction.copy(state = FinanceTransactionState.Done, id = 2),
            transaction.copy(state = FinanceTransactionState.Done, id = 3),
            transaction.copy(state = FinanceTransactionState.Done, id = 4)
    )

    @Before
    override fun onInit() {
        super.onInit()
        presenter = HistoryPresenter(
                view,
                getDoneTransactionsInteractor,
                router,
                rvAdapter
        )
    }

    override fun onMockInit() {
        getDoneTransactionsInteractor = GetDoneTransactionsInteractor(dataSource, testScheduler, testScheduler)
    }

    @Test
    fun getAdapter() {
        presenter.adapter
        presenter.getAdapter()
    }

    @Test fun getInteractors(){
        presenter.getDoneTransactionsInteractor
    }

    @Test
    fun onStart() {
        doReturn(Flowable.just(listDoneTransactions)).`when`(dataSource).getDoneTransactions()
        presenter.onStart()
        testScheduler.triggerActions()
        Mockito.verify(view).showTransactions(any())
    }

    @Test
    fun onStop() {
        presenter.onStop()
    }

}