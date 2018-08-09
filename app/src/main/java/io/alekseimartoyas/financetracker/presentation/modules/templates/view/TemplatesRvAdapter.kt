package io.alekseimartoyas.financetracker.presentation.modules.templates.view

import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter.POSITION_NONE
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.OperationType
import java.text.DecimalFormat


class TemplatesRvAdapter(val onClick: (FinanceTransaction) -> Unit,
                         val onDelete: (FinanceTransaction) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var transactions: Array<FinanceTransaction> = arrayOf()
    private val decimalFormat = DecimalFormat("0.00")

    fun setData(transactions: Array<FinanceTransaction>) {
        this.transactions = transactions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_scheduled_transaction, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).apply {
            val item = transactions[position]

            category.setText(item.category.strId)
            date.text = item.date

            var amount = decimalFormat.format(item.quantity) + " " + itemView.context.getString(item.currency.strId)

            when (item.operationType) {
                OperationType.ENLISTMENT -> {
                    amount = "+ $amount"
                    this.amount.setTextColor(ContextCompat.getColor(itemView.context, R.color.yandex_blue))
                }
                OperationType.DEBIT -> {
                    amount = "- $amount"
                    this.amount.setTextColor(ContextCompat.getColor(itemView.context, R.color.yandex_red))
                }
            }

            this.amount.text = amount
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val category = view.findViewById<TextView>(R.id.category)
        val date = view.findViewById<TextView>(R.id.date)
        val amount = view.findViewById<TextView>(R.id.amount)
        val icDelete = view.findViewById<View>(R.id.ic_delete)

        init {
            if (adapterPosition != POSITION_NONE) {
                icDelete.setOnClickListener {
                    onDelete(transactions[adapterPosition])
                }
                itemView.setOnClickListener {
                    onClick(transactions[adapterPosition])
                }
            }
        }
    }
}