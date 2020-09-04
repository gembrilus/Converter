package iv.nakonechnyi.exchange.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import iv.nakonechnyi.exchange.Repository
import iv.nakonechnyi.exchange.UnitTestHelper
import iv.nakonechnyi.exchange.clients.ApiClient
import iv.nakonechnyi.exchange.db.HistoryDao
import iv.nakonechnyi.exchange.getOrAwaitValue
import iv.nakonechnyi.exchange.model.ConvertOperation
import iv.nakonechnyi.exchange.model.Currency
import iv.nakonechnyi.exchange.model.CurrencyResponse
import iv.nakonechnyi.exchange.service.FixerConverterService
import iv.nakonechnyi.exchange.utils.MainThreadDispatcher
import iv.nakonechnyi.exchange.utils.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Matchers
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.verification.VerificationMode

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ConverterViewModelTest {

    private lateinit var list: List<ConvertOperation>
    private lateinit var dao: HistoryDao
    private lateinit var apiClient: ApiClient
    private lateinit var service: FixerConverterService
    private lateinit var repository: Repository
    private lateinit var dispatcher: CoroutineDispatcher
    private lateinit var model: ConverterViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()     //mock MainLooper

    @Before
    fun setUp() {

        Dispatchers.setMain(MainThreadDispatcher)

        list = UnitTestHelper.generateListOfConvertOperation()
        dao = mock{
            on { runBlocking { getAllRecords() } } doReturn list
            onGeneric { runBlocking { saveOperation(any()) } } doReturn 0
        }
        service = mock()
        apiClient = mock{
            on { runBlocking { getService() } } doReturn service
        }

        repository = Repository(apiClient, dao)
        dispatcher = Dispatchers.Main

        model = ConverterViewModel(repository, dispatcher)

    }

    @After
    fun after(){
        Dispatchers.resetMain()
    }

    @Test
    fun whenInvokedConvertIsSuccessThanResultIsReceived() = runBlocking {

        val expectedResult = 1.0
        val expected = CurrencyResponse(null, null, expectedResult)

        whenever(service.convert(any(), any(), any())) doReturn expected

        model.convert(Currency.UAH, Currency.USD, 10)

        val actual = model.result.getOrAwaitValue()

        assertEquals(expectedResult, actual, 0.001)

        verify(apiClient, atLeastOnce()).getService()
        verify(service, atLeastOnce()).convert(any(), any(), any())
        verify(dao, atLeastOnce()).saveOperation(any())
        verify(dao, never()).getAllRecords()
        verify(dao, never()).delete(any())
        verifyNoMoreInteractions(apiClient)

    }

    @Test
    fun whenInvokedConvertIsErrorThanResultWithErrorMessageIsReceived() = runBlocking {

        val expectedResult = 1.0
        val errorCode = 404
        val errorMessage = "Page not found"
        val expected = CurrencyResponse(errorCode, errorMessage, expectedResult)

        whenever(service.convert(any(), any(), any())) doReturn expected

        model.convert(Currency.UAH, Currency.USD, 10)

        val actual = model.result.getOrAwaitValue()
        val error = model.error.getOrAwaitValue() as ResultWrapper.Error

        assertNotEquals(expectedResult, actual, 0.001)
        assertEquals(errorCode, error.value.error)
        assertEquals(errorMessage, error.value.errorMessage)

        verify(apiClient, atLeastOnce()).getService()
        verify(service, atLeastOnce()).convert(any(), any(), any())
        verify(dao, never()).saveOperation(any())
        verify(dao, never()).getAllRecords()
        verify(dao, never()).delete(any())
        verifyNoMoreInteractions(service)
        verifyNoMoreInteractions(dao)
        verifyNoMoreInteractions(apiClient)

    }

    @Test
    fun whenDoRequestHistoryFromDbThanPropertyHistoryIsChanged() {

        val history = model.history.getOrAwaitValue()

        assertNotNull(history)
        assertEquals(list, history)

    }

    @Test
    fun whenMethodOpenHistoryFragmentIsInvokedThanPropertyOpenHistoryIsSetTwice() {
        val list = mutableListOf<Boolean>()
        val observer = Observer(list::add)

        model.openHistory.observeForever(observer)
        model.openHistoryFragment()
        model.openHistory.removeObserver(observer)


        assertNotEquals(list.size, 2)
        assertTrue(list.containsAll(listOf(true, false)))
    }

    @Test
    fun whenMethodOpenHistoryFragmentIsInvokedThanPropertyOpenHistoryIsReturnedToFalse() {

        val start = model.openHistory.getOrAwaitValue()

        model.openHistoryFragment()

        val end = model.openHistory.getOrAwaitValue()

        assertTrue(start == end)
    }
}