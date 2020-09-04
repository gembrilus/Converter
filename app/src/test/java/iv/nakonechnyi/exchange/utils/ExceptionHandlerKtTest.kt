package iv.nakonechnyi.exchange.utils

import iv.nakonechnyi.exchange.model.CurrencyResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ExceptionHandlerKtTest {

    @Test(expected = RuntimeException::class)
    fun whenSafeApiCallIsCalledThanApiCallIsInvokedByThrowRunTimeException() = runBlocking<Unit> {

        val apiCall: suspend () -> CurrencyResponse = { throw RuntimeException()}

        safeApiCall(apiCall)

    }

    @Test
    fun whenSafeApiCallIsCalledThanIFApiCallReturnSuccessResultApiCallReturnResult() = runBlocking {

        val expected = CurrencyResponse(null, null, 0.0)
        val apiCall: suspend () -> CurrencyResponse = { expected }

        val result = safeApiCall(apiCall)

        assert(result is ResultWrapper.Success)
        assert(result == ResultWrapper.Success(expected))

    }

    @Test
    fun whenSafeApiCallIsCalledThanIFApiCallReturnErrorResultAndResultHasErrorFields() = runBlocking {

        val expected = CurrencyResponse(404, "Page Not Found", 1.1)
        val apiCall: suspend () -> CurrencyResponse = { expected }

        val result = safeApiCall(apiCall)

        assert(result is ResultWrapper.Error)
        assert((result as ResultWrapper.Error).value == expected)
        assert(result.value.error != null)
        assert(result.value.errorMessage != null)

    }
}