package iv.nakonechnyi.exchange.ui.fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import iv.nakonechnyi.exchange.ui.MainActivity
import iv.nakonechnyi.exchange.ui.viewmodels.ConverterViewModel

abstract class BaseFragment : Fragment() {

    protected val model by lazy {
        ViewModelProvider(requireActivity(), (requireActivity() as MainActivity).viewModelFactory)
            .get(ConverterViewModel::class.java)
    }

}