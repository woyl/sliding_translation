package com.custom_view_1.adpter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Administrator on 2018/8/2/002.
 */
class SuperHolder(itemView: View, var TYPE: Int) : RecyclerView.ViewHolder(itemView) {

    var convertView: View
        internal set

    init {
        convertView = itemView
    }

    fun <T : View> getView(viewId: Int): T {
        return convertView.findViewById<View>(viewId) as T
    }
}