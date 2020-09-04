package iv.nakonechnyi.exchange.utils

import android.view.View
import android.widget.AdapterView
import iv.nakonechnyi.exchange.model.CurrencyResponse

class WrapperOnItemSelectedListener(private val onSelect: ()->Unit, private val onNothing: ()->Unit = {}): AdapterView.OnItemSelectedListener{
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) = onSelect()
        override fun onNothingSelected(parent: AdapterView<*>?) = onNothing()
}


sealed class ResultWrapper {
    data class Success(val value: CurrencyResponse): ResultWrapper()
    data class Error(val value: CurrencyResponse): ResultWrapper()
}