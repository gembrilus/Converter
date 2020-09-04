package iv.nakonechnyi.exchange.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputEditText
import iv.nakonechnyi.exchange.R
import iv.nakonechnyi.exchange.databinding.FragmentMainBinding
import iv.nakonechnyi.exchange.model.Currency
import iv.nakonechnyi.exchange.utils.WrapperOnItemSelectedListener
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val listener by lazy { WrapperOnItemSelectedListener(this::update) }

    private val spinnerAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            Currency.values().map { getString(it.resIdName) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentMainBinding>(inflater, R.layout.fragment_main, container,false)
        .apply {
            lifecycleOwner = this@MainFragment
            viewModel = model
        }
        .run { root }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupEditText(amount)
        setupSpinner(from_currency)
        setupSpinner(to_currency)

    }

    private fun update() {
        val amount = amount.text.toString().toInt()
        if (amount == 0) return
        val from = Currency.values()[from_currency.selectedItemPosition]
        val to = Currency.values()[to_currency.selectedItemPosition]
        model.convert(from, to, amount)
    }

    private fun setupSpinner(spinner: Spinner) = with(spinner) {
        onItemSelectedListener = listener
        adapter = spinnerAdapter
    }

    private fun setupEditText(v: TextInputEditText) = with(v) {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                update()
                true
            } else false
        }
    }
}