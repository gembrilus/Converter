package iv.nakonechnyi.exchange.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import iv.nakonechnyi.exchange.db.ConvertRoomDatabase
import iv.nakonechnyi.exchange.db.HistoryDao
import iv.nakonechnyi.exchange.db.HistoryDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): HistoryDatabase =
        Room.databaseBuilder(context, ConvertRoomDatabase::class.java, "history.db").build()


    @Provides
    @Singleton
    fun provideROomDao(db: HistoryDatabase): HistoryDao = db.getDao()

}