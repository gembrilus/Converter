package iv.nakonechnyi.exchange.ui.viewmodels

import androidx.lifecycle.*
import iv.nakonechnyi.exchange.Repository
import iv.nakonechnyi.exchange.model.ConvertOperation
import iv.nakonechnyi.exchange.model.Currency
import iv.nakonechnyi.exchange.utils.ResultWrapper
import iv.nakonechnyi.exchange.utils.ResultWrapper.Error
import iv.nakonechnyi.exchange.utils.ResultWrapper.Success
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named

class ConverterViewModel @Inject constructor(
    private val repository: Repository,
    @Named("IO") private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _result: MutableLiveData<Double> = MutableLiveData(0.0)
    val result: LiveData<Double> get() = _result

    private val _error: MutableLiveData<ResultWrapper> = MutableLiveData()
    val error: LiveData<ResultWrapper> get() = _error

    fun convert(from: Currency, to: Currency, amount: Int) = viewModelScope.launch(dispatcher) {

        when (val res = repository.convert(from, to, amount)) {
            is Success -> {
                postValue(_error, res)

                val result = res.value.amount.also {
                    postValue(_result, it)
                }

                val op = ConvertOperation(amount, from, to, result)
                saveToHistory(op)
            }
            is Error -> postValue(_error, res)
        }

    }

    val history: LiveData<List<ConvertOperation>> = liveData(dispatcher) {  emit(repository.getHistory()) }

    private fun saveToHistory(op: ConvertOperation) = viewModelScope.launch(dispatcher) {
        repository.saveToHistory(op)
    }

    private val _openHistory = MutableLiveData(false)
    val openHistory: LiveData<Boolean> get() = _openHistory

    fun openHistoryFragment() {
        _openHistory.value = true
        _openHistory.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    private suspend fun <T> postValue(liveData: MutableLiveData<T>, value: T){
        withContext(Dispatchers.Main){
            liveData.value = value
        }
    }

}