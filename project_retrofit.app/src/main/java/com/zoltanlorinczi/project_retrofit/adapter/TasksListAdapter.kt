package com.zoltanlorinczi.project_retrofit.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.GroupViewModelFactory
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Author:  Zoltan Lorinczi
 * Date:    12/6/2021
 */
class TasksListAdapter(
        private var list: ArrayList<TaskResponse>,
        private val context: FragmentActivity,
        private val listener: OnItemClickListener,
        private val listener2: OnItemLongClickListener
) :
        RecyclerView.Adapter<TasksListAdapter.DataViewHolder>() {

    private lateinit var usersViewModel: UsersViewModel;
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener, View.OnLongClickListener {

        val taskAssigneeView: TextView = itemView.findViewById(R.id.task_assignee_view)
        val taskTitleTextView: TextView = itemView.findViewById(R.id.userNameList)
        val taskDeadline: TextView = itemView.findViewById(R.id.task_deadline_view)
        val taskDescriptionTextView: TextView = itemView.findViewById(R.id.task_description_view)
        val taskPriorityTextView: TextView = itemView.findViewById(R.id.task_priority_view)


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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tasks_list_item, parent, false)

        val factory = UsersViewModelFactory(ThreeTrackerRepository())
        usersViewModel = ViewModelProvider(context, factory)[UsersViewModel::class.java]
        return DataViewHolder(itemView)
    }


    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = list[position]

        holder.taskTitleTextView.text = currentItem.title
        holder.taskDescriptionTextView.text = currentItem.description
        holder.taskAssigneeView.text = getCreatedByName(currentItem.createdByUserID);
        holder.taskDeadline.text = getDateTime(currentItem.deadline);
        if (currentItem.priority == 0) {
            holder.taskPriorityTextView.setBackgroundColor(Color.RED)
            holder.taskPriorityTextView.text = "HIGH"
        } else if (currentItem.priority == 1) {
            holder.taskPriorityTextView.setBackgroundColor(Color.YELLOW)
            holder.taskPriorityTextView.text = "MEDIUM"
        } else if (currentItem.priority == 2) {
            holder.taskPriorityTextView.text = "LOW"
            holder.taskPriorityTextView.setBackgroundColor(Color.GREEN)
        }

    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newList: ArrayList<TaskResponse>) {
        list = newList
    }

    fun getDateTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(s.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    fun getCreatedByName(id:Int) : String{
        for (item in usersViewModel.users.value!!){
            if(item.id == id){
                return item.first_name +" "+ item.last_name
            }
        }
        return "None"
    }
}