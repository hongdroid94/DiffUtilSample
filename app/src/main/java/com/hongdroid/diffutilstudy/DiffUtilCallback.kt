package com.hongdroid.diffutilstudy

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(private val oldList: ArrayList<UserModel>,
                       private val newList: ArrayList<UserModel>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}