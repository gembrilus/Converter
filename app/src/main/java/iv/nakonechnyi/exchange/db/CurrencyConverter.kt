package iv.nakonechnyi.exchange.db

import androidx.room.TypeConverter
import iv.nakonechnyi.exchange.model.Currency

class CurrencyConverter {

    @TypeConverter
    fun fromCurrency(currency: Currency) = currency.name

    @TypeConverter
    fun toCurrency(value: String) = Currency.valueOf(value)

}