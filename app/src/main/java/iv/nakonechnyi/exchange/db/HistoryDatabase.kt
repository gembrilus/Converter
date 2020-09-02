package iv.nakonechnyi.exchange.db

interface HistoryDatabase {
    fun getDao(): HistoryDao
}