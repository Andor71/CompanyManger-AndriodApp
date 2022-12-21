package com.zoltanlorinczi.project_retrofit.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentTaskBinding
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class TaskFragment : Fragment(R.layout.fragment_task) {
    private lateinit var tasksViewModel: TasksViewModel
    private var task: TaskResponse? = null;

    private lateinit var binding: FragmentTaskBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        val factory = TasksViewModelFactory(ThreeTrackerRepository())
        tasksViewModel = ViewModelProvider(requireActivity(), factory)[TasksViewModel::class.java]

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)

        navBar?.visibility = View.VISIBLE;
        binding = FragmentTaskBinding.inflate(inflater);
        task = tasksViewModel.products.value?.get(tasksViewModel.ID);
        setProperties();
        return binding.root;
    }

    fun setProperties(){
        binding.taskNameText.text = task?.title;
        binding.descriptionText.text = task?.description;
        binding.assignedDateText.text =getDateTime(task?.createdTime!!);
        if (task?.priority == 0) {
            binding.priotityText.setBackgroundColor(Color.RED)
            binding.priotityText.text = "HIGH";
        } else if (task?.priority == 1) {
            binding.priotityText.setBackgroundColor(Color.YELLOW)
            binding.priotityText.text = "MEDIUM";
        } else if (task?.priority == 2) {
            binding.priotityText.setBackgroundColor(Color.GREEN)
            binding.priotityText.text = "LOW";
        }
    }
    fun getDateTime(s: Long): String {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(s.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}