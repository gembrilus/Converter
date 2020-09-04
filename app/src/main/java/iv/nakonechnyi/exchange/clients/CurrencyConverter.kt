package iv.nakonechnyi.exchange.clients

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import iv.nakonechnyi.exchange.model.Currency

class CurrencyConverter {

    @ToJson
    fun currencyToJson(currency: Currency) = currency.name

    @FromJson
    fun jsonToCurrency(json: String) = Currency.valueOf(json)

}