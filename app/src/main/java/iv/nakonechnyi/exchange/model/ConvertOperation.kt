package iv.nakonechnyi.exchange.model

import androidx.room.Entity
import androidx.room.TypeConverters
import iv.nakonechnyi.exchange.db.CurrencyConverter

@Entity(tableName = "convert_history", primaryKeys = ["amount", "from", "to", "result"])
@TypeConverters(CurrencyConverter::class)
data class ConvertOperation(
    val amount: Int,
    val from: Currency,
    val to: Currency,
    val result: Double
)