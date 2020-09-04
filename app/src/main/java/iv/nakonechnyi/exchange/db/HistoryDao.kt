package iv.nakonechnyi.exchange.db

import androidx.room.*
import iv.nakonechnyi.exchange.model.ConvertOperation

@Dao
interface HistoryDao {

    @Query("SELECT * FROM convert_history")
    suspend fun getAllRecords(): List<ConvertOperation>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveOperation(operation: ConvertOperation): Long

    @Delete
    fun delete(op: ConvertOperation): Int
}