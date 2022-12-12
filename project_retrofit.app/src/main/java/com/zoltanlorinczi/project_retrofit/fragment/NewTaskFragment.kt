package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentNewTaskBinding
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.TaskDto
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModelFactory

class NewTaskFragment : Fragment() {
    private lateinit var tasksViewModel: TasksViewModel
    private var task: TaskResponse? = null;

    private lateinit var binding: FragmentNewTaskBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = TasksViewModelFactory(ThreeTrackerRepository())
        tasksViewModel = ViewModelProvider(requireActivity(), factory)[TasksViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskBinding.inflate(inflater);

        binding.createBtn.setOnClickListener {
            createNewTask();
        }

        return binding.root
    }

    fun createNewTask(){
        val title = binding.taskTitle.text.toString();
        val description = binding.description.text.toString();
        val assigneToUser = 4
        val priority = 1;
        val deadline = 1625942327;
        val departmentID= 2;
        val status = 0;

        val newTask =  TaskDto(1,title,description,assigneToUser,priority, deadline.toLong(),departmentID,status);

        tasksViewModel.createTask(newTask);
        findNavController().navigate(R.id.action_newTaskFragment_to_listFragment);
    }
}