package com.zoltanlorinczi.project_retrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retrofit.api.model.ActivityResponse
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse

/**
 * Author:  Zoltan Lorinczi
 * Date:    12/6/2021
 */
class ActivitesListAdapter(
    private var list: ArrayList<ActivityResponse>,
    private val context: Context,
    private val listener: OnItemClickListener,
    private val listener2: OnItemLongClickListener
) :
    RecyclerView.Adapter<ActivitesListAdapter.SimpleDataViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    open inner class SimpleDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        override fun onLongClick(v: View?): Boolean {
            TODO("Not yet implemented")
        }
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : SimpleDataViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)

        }

        override fun onLongClick(p0: View?): Boolean {
            val currentPosition = this.adapterPosition
            listener2.onItemLongClick(currentPosition)
            return true
        }
    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleDataViewHolder {
        return when (viewType) {
            ActivitesType.DEPARTMENT.value -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.departmentactivityitem, parent, false)
                SimpleDataViewHolder(itemView)
            }
            ActivitesType.ANOUCEMNET.value -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.anouncmentactivityitem, parent, false)
                DataViewHolder(itemView)
            }
            ActivitesType.TASK.value -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.taskactivityitem, parent, false)
                DataViewHolder(itemView)
            }
            else -> {
                throw IllegalStateException("Type is not supported!")
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentItem = list[position]
        if(currentItem.type == 0){
            return ActivitesType.DEPARTMENT.value
        }
        if(currentItem.type == 1){
            return  ActivitesType.TASK.value
        }
          return  ActivitesType.ANOUCEMNET.value

    }

    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: SimpleDataViewHolder, position: Int) {
//        if (getItemViewType(position) == ActivitesType.DEPARTMENT.value) {
//            val complexHolder = (holder as DataViewHolder)
//            val currentItem = list[position]
//
//            complexHolder.taskTitleTextView.text = currentItem.title
//            complexHolder.taskDescriptionTextView.text = currentItem.description
//
//            when (currentItem.priority) {
//                0 -> {
//                    complexHolder.taskPriorityTextView.setBackgroundColor(Color.RED)
//                }
//                1 -> {
//                    complexHolder.taskPriorityTextView.setBackgroundColor(Color.YELLOW)
//                }
//                2 -> {
//                    complexHolder.taskPriorityTextView.setBackgroundColor(Color.GREEN)
//                }
//            }
//
////            Glide.with(context)
////                .load(R.drawable.ic_launcher_background)
////                //.load("https://devinit.org/assets/img/profile-fallback.e7a6f788830c.jpg")
////                //.placeholder(R.drawable.ic_launcher_background)
////                .override(100, 100)
////                .into(complexHolder.taskOwnerProfileImage)
//        }
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newList: ArrayList<ActivityResponse>) {
        list = newList
    }

    private enum class ActivitesType(val value: Int) {
        DEPARTMENT(0),
        TASK(1),
        ANOUCEMNET(2)
    }
}