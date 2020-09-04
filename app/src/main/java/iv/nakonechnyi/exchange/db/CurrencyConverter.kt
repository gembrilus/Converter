package iv.nakonechnyi.exchange.db

import androidx.room.TypeConverter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import iv.nakonechnyi.exchange.model.Currency

class CurrencyConverter {

    @TypeConverter
    @ToJson
    fun fromCurrency(currency: Currency) = currency.name

    @TypeConverter
    @FromJson
    fun toCurrency(value: String) = Currency.valueOf(value)

}