package com.hongdroid.diffutilstudy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val arrayList: ArrayList<UserModel>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var mListener: CommonListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false), mListener)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    fun setListener(listener: CommonListener) {
        mListener = listener
    }

    fun changeList(newHourlyWeatherList: ArrayList<UserModel>) {
        val diffCallback = DiffUtilCallback(arrayList, newHourlyWeatherList)
        val diffResult = DiffUtil.calculateDiff(diffCallback) // 내부적으로 알고리즘을 써 변경된 아이템을 감지

        arrayList.clear()
        arrayList.addAll(newHourlyWeatherList)

        diffResult.dispatchUpdatesTo(this) // 업데이트 이벤트를 전달
    }

    class ViewHolder(view: View, listener: CommonListener) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tv_user_name)
        private val tvAge: TextView = view.findViewById(R.id.tv_user_age)
        private val tvHobby: TextView = view.findViewById(R.id.tv_hobby)

        init {
            view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(user : UserModel) {
            tvName.text = user.name
            tvAge.text = user.age.toString()
            tvHobby.text = user.hobby
        }
    }
}
