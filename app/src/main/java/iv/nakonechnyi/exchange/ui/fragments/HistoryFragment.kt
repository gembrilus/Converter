package iv.nakonechnyi.exchange.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import iv.nakonechnyi.exchange.R
import iv.nakonechnyi.exchange.ui.HistoryAdapter
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : BaseFragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private val historyAdapter: HistoryAdapter by lazy {
        HistoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_history, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(history_list) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }

        model.history.observe(requireActivity(), { historyAdapter.addRecords(it) })

    }


}