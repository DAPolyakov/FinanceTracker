package io.alekseimartoyas.financetracker.presentation

import io.alekseimartoyas.financetracker.base.BaseUnitTest
import io.alekseimartoyas.financetracker.base.any
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.data.services.DataSource
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.domain.interactors.GetTemplateTransactionsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.UpdateFinanceTransactionInteractor
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.financetracker.presentation.modules.templates.presenter.TemplatesPresenter
import io.alekseimartoyas.financetracker.presentation.modules.templates.view.TemplatesFragment
import io.alekseimartoyas.financetracker.presentation.modules.templates.view.TemplatesRvAdapter
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito


class TemplatesPresenterUnitTest : BaseUnitTest() {

    lateinit var presenter: TemplatesPresenter

    @Mock
    lateinit var view: TemplatesFragment

    lateinit var getTemplateTransactionsInteractor: GetTemplateTransactionsInteractor
    lateinit var updateFinanceTransactionInteractor: UpdateFinanceTransactionInteractor

    @Mock
    lateinit var router: IMainActivityRouterInput
    @Mock
    lateinit var dataSource: DataSource

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

    @Before
    override fun onInit() {
        super.onInit()
        presenter = TemplatesPresenter(
                view,
                getTemplateTransactionsInteractor,
                updateFinanceTransactionInteractor,
                router
        )
    }

    override fun onMockInit() {
        getTemplateTransactionsInteractor = GetTemplateTransactionsInteractor(dataSource, testScheduler, testScheduler)
        updateFinanceTransactionInteractor = UpdateFinanceTransactionInteractor(dataSource, testScheduler, testScheduler)
    }

    @Test
    fun onStart() {
        Mockito.doReturn(Flowable.just(listTemplateTransactions)).`when`(dataSource).getTemplateTransactions()
        presenter.onStart()
        testScheduler.triggerActions()
        Mockito.verify(view).showTransactions(any())
    }

    @Test
    fun onStop() {
        presenter.onStop()
    }

    @Test
    fun getAdapter() {
        presenter.adapter = TemplatesRvAdapter({}, {})
    }


}