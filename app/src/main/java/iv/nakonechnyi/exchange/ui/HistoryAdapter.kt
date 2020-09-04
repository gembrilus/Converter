package iv.nakonechnyi.exchange.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import iv.nakonechnyi.exchange.R
import iv.nakonechnyi.exchange.model.ConvertOperation
import kotlinx.android.synthetic.main.recycleview_history_holder.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val list: MutableList<ConvertOperation> = mutableListOf()

    fun addRecords(ops: List<ConvertOperation>) {
        list.addAll(ops)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recycleview_history_holder, parent, false)
            .run(::HistoryViewHolder)

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount() = list.size

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(op: ConvertOperation) {
            itemView.item_id.text = "${adapterPosition}"
            itemView.item_holder.text = itemView.context.getString(R.string.item_holder_text, op.amount, op.from, op.result, op.to)
        }
    }
}