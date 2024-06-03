package com.motax.modutaxi.presentation.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

//@BindingAdapter("list")
//fun <T, VH : RecyclerView.ViewHolder> bindList(recyclerView: RecyclerView, list: List<T>?) {
//    val adapter = recyclerView.adapter as ListAdapter<T, VH>
//    adapter?.submitList(list)
//}

@BindingAdapter("list")
fun <T> bindList(recyclerView: RecyclerView, list: List<T>?) {
    val adapter = recyclerView.adapter
    if (adapter is ListAdapter<*, *>) {
        (adapter as ListAdapter<T, *>).submitList(list)
    }
}