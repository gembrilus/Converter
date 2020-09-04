package iv.nakonechnyi.exchange.ui.viewmodels

import androidx.lifecycle.*
import iv.nakonechnyi.exchange.Repository
import iv.nakonechnyi.exchange.model.ConvertOperation
import iv.nakonechnyi.exchange.model.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConverterViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _result: MutableLiveData<Double> = MutableLiveData(0.0)
    val result: LiveData<Double> get() = _result

    fun convert(from: Currency, to: Currency, amount: Int) = viewModelScope.launch(Dispatchers.IO) {
        val res = repository.convert(from, to, amount)

        withContext(Dispatchers.Main) {
            _result.value = res
        }

        val op = ConvertOperation(0, amount, from, to, res)
        saveToHistory(op)

    }

    val history: LiveData<List<ConvertOperation>> = liveData(Dispatchers.IO) {  emit(repository.getHistory()) }

    private fun saveToHistory(op: ConvertOperation) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveToHistory(op)
    }

    private val _openHistory = MutableLiveData(false)
    val openHistory: LiveData<Boolean> get() = _openHistory

    fun openHistoryFragment() {
        _openHistory.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}