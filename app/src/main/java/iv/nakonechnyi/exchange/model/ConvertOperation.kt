package iv.nakonechnyi.exchange.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import iv.nakonechnyi.exchange.db.CurrencyConverter

@Entity(tableName = "convert_history")
@TypeConverters(CurrencyConverter::class)
data class ConvertOperation(
    @PrimaryKey val id: Long,
    val amount: Double,
    val from: Currency,
    val to: Currency,
    val result: Double
)