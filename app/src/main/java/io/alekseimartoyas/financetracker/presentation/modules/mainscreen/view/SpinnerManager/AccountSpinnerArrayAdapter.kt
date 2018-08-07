package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.SpinnerManager

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import io.alekseimartoyas.financetracker.data.local.Account


class AccountSpinnerArrayAdapter(context: Context, private val items: Array<Account>)
    : ArrayAdapter<Account>(context, android.R.layout.simple_spinner_dropdown_item, items) {

    private fun getCustomView(position: Int, convertView: View?): View? {
        var view = convertView
        if (view == null)
            view = View.inflate(context, android.R.layout.simple_spinner_dropdown_item, null)

        (view as TextView).text = items[position].title
        return view
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        return getCustomView(position, convertView)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        return getCustomView(position, convertView)
    }
}