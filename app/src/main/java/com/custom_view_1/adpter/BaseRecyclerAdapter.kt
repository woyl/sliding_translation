package com.custom_view_1.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Administrator on 2018/8/2/002.
 */
abstract class BaseRecyclerAdapter<T>(private val context: Context?, private val layoutId: Int, private val data: MutableList<T>) : RecyclerView.Adapter<SuperHolder>() {

    protected val TYPE_HEADER = 1
    protected val TYPE_NORMAL = 2
    private var headId = -1

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (getItemViewType(position) == TYPE_HEADER)
                        manager.spanCount
                    else
                        1
                }
            }
        }
    }

    fun addHeadView(layoutId: Int) {
        headId = layoutId
    }

    init {
        notifyDataSetChanged()
    }

    fun setData(list: List<T>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun getData(): List<T> {
        return data
    }

    //  添加数据
    fun addData(posion:Int,list: List<T>){
        //      在list中添加数据，并通知条目加入一条
//        data.add(posion,list[posion])
        notifyItemChanged(posion)
    }


    fun addData(list: List<T>) {
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun removeData(position: Int) {
        //position=holder.getLayoutPosition()
        if (position <= data.size - 1) {
            data.removeAt(position)
//            notifyItemChanged(position)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHolder {
        return if (viewType == TYPE_HEADER) {
            SuperHolder(LayoutInflater.from(context).inflate(headId, null, false), viewType)
        } else
            SuperHolder(LayoutInflater.from(context).inflate(layoutId, null, false), viewType)
    }

    override fun onBindViewHolder(holder: SuperHolder, position: Int) {
        if (holder.TYPE == TYPE_HEADER) {
            bindData(holder, position, null)
        } else {
            if (headId == -1)
                bindData(holder, position, data[position])
            else
                bindData(holder, position, data[position - 1])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (headId == -1) {
            TYPE_NORMAL
        } else {
            if (position == 0)
                TYPE_HEADER
            else
                TYPE_NORMAL
        }
    }

    override fun getItemCount(): Int {
        return if (headId == -1) data.size else data.size + 1
    }

    abstract fun bindData(holder: SuperHolder, position: Int, bean: T?)
}