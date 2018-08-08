package io.alekseimartoyas.financetracker.presentation.modules.templates.view

import android.support.v4.view.PagerAdapter.POSITION_NONE
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction


class TemplatesRvAdapter(val onClick: (FinanceTransaction) -> Unit,
                         val onDelete: (FinanceTransaction) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var transactionList: Array<FinanceTransaction> = arrayOf()

    fun setData(transactions: Array<FinanceTransaction>) {
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
        (holder as ViewHolder).apply {
            categoryText.text = transactionList[position].category.toString()
            currencyText.text = transactionList[position].currency.toString()
            operationTypeText.text = transactionList[position].operationType.toString()
            quantityCurrencyText.text = transactionList[position].quantity.toString()
            data.text = transactionList[position].date
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryText = view.findViewById<TextView>(R.id.category_text)!!
        val operationTypeText = view.findViewById<TextView>(R.id.operation_type_text)!!
        val quantityCurrencyText = view.findViewById<TextView>(R.id.currency_quantity_text)!!
        val currencyText = view.findViewById<TextView>(R.id.currency_text)!!
        val data: TextView = view.findViewById<TextView>(R.id.date_tv)
        val icDelete = view.findViewById<View>(R.id.ic_delete)

        init {
            if (adapterPosition != POSITION_NONE) {
                icDelete.setOnClickListener {
                    onDelete(transactionList[adapterPosition])
                }
                itemView.setOnClickListener {
                    onClick(transactionList[adapterPosition])
                }
            }
        }
    }
}