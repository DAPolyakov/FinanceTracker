package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.Account


class AccountRvAdapter(val onAccount: (Account) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selected = 0
    private var prevSelected = 0

    private var data: Array<Account> = arrayOf()

    fun setData(newData: Array<Account>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_account, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).fill(data[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.title)!!

        fun fill(item: Account) {
            title.text = item.title
            title.alpha = if (selected == adapterPosition) 1.0f else 0.3f
            title.setOnClickListener(null)
            title.setOnClickListener {
                onAccount(item)
                prevSelected = selected
                selected = adapterPosition
                notifyItemChanged(prevSelected)
                notifyItemChanged(selected)
            }

        }
    }

}