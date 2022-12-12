package com.zoltanlorinczi.project_retrofit.fragment

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
        binding.taskTitleDetail.text = task?.title;
        binding.TaskDescriptionDetail.text = task?.description;
        binding.assignedDateDetail.text = task?.createdTime.toString();

    }
}