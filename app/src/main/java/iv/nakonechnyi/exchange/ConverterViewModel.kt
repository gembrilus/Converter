package iv.nakonechnyi.exchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iv.nakonechnyi.exchange.model.ConvertOperation
import iv.nakonechnyi.exchange.model.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConverterViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _result: MutableLiveData<Double> = MutableLiveData(0.0)
    val result: LiveData<Double> get() = _result

    fun convert(from: Currency, to: Currency, amount: Int) {
        _result.value = repository.convert(from, to, amount).value
    }

    val history = repository.getHistory()

    fun saveToHistory(op: ConvertOperation) = viewModelScope.launch(Dispatchers.IO) {
        val id = repository.saveToHistory(op)
        Log.d("CONVERTER", "Saved to database! ID = $id")
    }

}