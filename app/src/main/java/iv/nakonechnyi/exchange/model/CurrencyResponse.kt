package iv.nakonechnyi.exchange.model

import com.squareup.moshi.Json

data class CurrencyResponse(
    val error: Int?,
    @Json(name ="error_message") val errorMessage: String?,
    val amount: Double = 0.0
)