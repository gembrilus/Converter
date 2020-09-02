package iv.nakonechnyi.exchange.db

import androidx.room.Database
import androidx.room.RoomDatabase
import iv.nakonechnyi.exchange.model.ConvertOperation

@Database(entities = [ConvertOperation::class], version = 1, exportSchema = false)
abstract class ConvertRoomDatabase: RoomDatabase(), HistoryDatabase