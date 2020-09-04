package iv.nakonechnyi.exchange.utils

import iv.nakonechnyi.exchange.model.CurrencyResponse

suspend fun safeApiCall(apiCall: suspend () -> CurrencyResponse): ResultWrapper {
    val response = apiCall()
    return if (response.error == null) {
        ResultWrapper.Success(response)
    } else {
        ResultWrapper.Error(response)
    }
}

fun showError(response: CurrencyResponse, message: (String) -> Unit) =
    message("Error ${response.error} : ${response.errorMessage}")
