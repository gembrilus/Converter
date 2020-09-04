package iv.nakonechnyi.exchange

import iv.nakonechnyi.exchange.clients.ApiClient
import iv.nakonechnyi.exchange.db.HistoryDao
import iv.nakonechnyi.exchange.model.ConvertOperation
import iv.nakonechnyi.exchange.model.Currency
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    @Named("fixer") private val apiClient: ApiClient,
    private val dao: HistoryDao
) {
    suspend fun convert(from: Currency, to: Currency, amount: Int) = apiClient.getService().convert(from, to, amount).amount
    suspend fun saveToHistory(op: ConvertOperation): Long = dao.saveOperation(op)
    suspend fun getHistory(): List<ConvertOperation> = dao.getAllRecords()

}