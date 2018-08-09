package io.alekseimartoyas.financetracker.base

import io.reactivex.schedulers.TestScheduler
import org.mockito.MockitoAnnotations


abstract class BaseUnitTest {

    protected val testScheduler = TestScheduler()

    open fun onInit() {
        MockitoAnnotations.initMocks(this)
        onMockInit()
    }

    abstract fun onMockInit()

}
