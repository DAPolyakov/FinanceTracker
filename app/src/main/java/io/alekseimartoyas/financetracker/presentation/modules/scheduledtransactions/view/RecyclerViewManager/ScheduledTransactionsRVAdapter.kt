package io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.view.RecyclerViewManager

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.presenter.IScheduledTransactionsRVInput


class ScheduledTransactionsRVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        IScheduledTransactionsRVInput {

    private var transactionList: Array<FinanceTransaction> = arrayOf()
    private var onDelete: (FinanceTransaction) -> Unit = {}

    override fun onDelete(onDelete: (FinanceTransaction) -> Unit) {
        this.onDelete = onDelete
    }

    override fun setData(transactions: Array<FinanceTransaction>) {
        transactionList = transactions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_scheduled_transaction, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.categoryText.text = transactionList[position].category.toString()
            holder.currencyText.text = transactionList[position].currency.toString()
            holder.operationTypeText.text = transactionList[position].operationType.toString()
            holder.quantityCurrencyText.text = transactionList[position].quantity.toString()
            holder.data.text = transactionList[position].date
            holder.icDelete.setOnClickListener {
                onDelete(transactionList[position])
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryText = view.findViewById<TextView>(R.id.category_text)!!
        val operationTypeText = view.findViewById<TextView>(R.id.operation_type_text)!!
        val quantityCurrencyText = view.findViewById<TextView>(R.id.currency_quantity_text)!!
        val currencyText = view.findViewById<TextView>(R.id.currency_text)!!
        val data: TextView = view.findViewById<TextView>(R.id.date_tv)
        val icDelete = view.findViewById<View>(R.id.ic_delete)
    }
}