package iv.nakonechnyi.exchange.service

import iv.nakonechnyi.exchange.model.Currency
import iv.nakonechnyi.exchange.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FixerConverterService {

    @GET("currency.php")
    suspend fun convert(
        @Query("from") from: Currency,
        @Query("to") to: Currency,
        @Query("amount") amount: Int
    ): CurrencyResponse
}