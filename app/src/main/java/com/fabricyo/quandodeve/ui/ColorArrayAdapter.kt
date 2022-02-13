package com.fabricyo.quandodeve.ui

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.fabricyo.quandodeve.R

class ColorArrayAdapter(
    private val mContext: Context,
    private val mLayoutResourceId: Int,
    colors: List<String>
) :
    ArrayAdapter<String>(mContext, mLayoutResourceId, colors) {
    private val cor: MutableList<String> = ArrayList(colors)
    var listenerSet: (String) -> Unit = {}

    override fun getCount(): Int {
        return cor.size
    }

    override fun getItem(position: Int): String {
        return cor[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = (mContext as Activity).layoutInflater
            convertView = inflater.inflate(mLayoutResourceId, parent, false)
        }
        try {
            val color: String = getItem(position)
            val colorAutoCompleteView =
                convertView!!.findViewById<View>(R.id.autoCompletetv) as TextView
            colorAutoCompleteView.text = color
            convertView.setOnClickListener { listenerSet(color) }
            val btnAutoCompleteView = convertView.findViewById<View>(R.id.btn_autoComplet) as Button
            btnAutoCompleteView.setBackgroundColor(Color.parseColor(color))
            btnAutoCompleteView.setOnClickListener { listenerSet(color) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertView!!
    }
}