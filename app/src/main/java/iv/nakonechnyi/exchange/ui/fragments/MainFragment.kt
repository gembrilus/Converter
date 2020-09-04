package iv.nakonechnyi.exchange.ui.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import iv.nakonechnyi.exchange.R
import iv.nakonechnyi.exchange.databinding.FragmentMainBinding
import iv.nakonechnyi.exchange.model.Currency
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val listener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) = update()
        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentMainBinding>(
        inflater,
        R.layout.fragment_main,
        container,
        false
    )
        .apply {
            lifecycleOwner = this@MainFragment
            viewModel = model
        }
        .run { root }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        amount.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                update()
                true
            } else {
                false
            }
        }
        from_currency.onItemSelectedListener = listener
        to_currency.onItemSelectedListener = listener

        btn_history.setOnClickListener {
            model.openHistoryFragment()
        }

    }

    private fun update(){
        val from = Currency.values()[from_currency.selectedItemPosition]
        val to = Currency.values()[to_currency.selectedItemPosition]
        val amount = amount.text.toString().toInt()
        model.convert(from, to, amount)
    }
}