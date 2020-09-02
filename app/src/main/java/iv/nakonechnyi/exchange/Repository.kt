package iv.nakonechnyi.exchange

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import iv.nakonechnyi.exchange.clients.ApiClient
import iv.nakonechnyi.exchange.db.HistoryDao
import iv.nakonechnyi.exchange.model.ConvertOperation
import iv.nakonechnyi.exchange.model.Currency
import iv.nakonechnyi.exchange.service.ConverterService
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiClient: ApiClient<out ConverterService>,
    private val dao: HistoryDao
) {

    fun convert(from: Currency, to: Currency, amount: Int): LiveData<Double> = liveData {
        emit(apiClient.getService().convert(from, to, amount).result)
    }

    suspend fun saveToHistory(op: ConvertOperation): Long = dao.saveOperation(op)

    fun getHistory(): LiveData<List<ConvertOperation>> = liveData { emit(dao.getAllRecords()) }

}