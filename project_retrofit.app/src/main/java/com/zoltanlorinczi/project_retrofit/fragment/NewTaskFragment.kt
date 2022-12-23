package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zoltanlorinczi.project_retorfit.R

import com.zoltanlorinczi.project_retorfit.databinding.FragmentNewTaskBinding
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.TaskDto
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.api.model.UserResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModelFactory
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class NewTaskFragment : Fragment() {
    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var usersViewModel: UsersViewModel;
    private var task: TaskResponse? = null;

    private lateinit var assigneOptions :Spinner;
    private lateinit var binding: FragmentNewTaskBinding;
    var assigneToUser :Int =0;
    var priority =0;
    var deadline = 0;
    var departmentID=4;
    var status = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = TasksViewModelFactory(ThreeTrackerRepository())
        tasksViewModel = ViewModelProvider(requireActivity(), factory)[TasksViewModel::class.java]
        val factoryuser =UsersViewModelFactory(ThreeTrackerRepository())
        usersViewModel = ViewModelProvider(requireActivity(),factoryuser)[UsersViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskBinding.inflate(inflater);

        binding.createBtn.setOnClickListener {
            createNewTask();
        }



        val arrayAdapter = ArrayAdapter(App.context,R.layout.support_simple_spinner_dropdown_item,usersViewModel.users.value!!);


        binding.assigneSpinner.adapter = arrayAdapter;
        binding.assigneSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                val toast = Toast.makeText(App.context, "Select an Assigne", Toast.LENGTH_SHORT)
                toast.show();
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                assigneToUser = usersViewModel.users.value!!.get(p2).id;
            }
        }

        val priorityList = arrayOf("HIGH","MEDIUM","LOW");

        val arrayAdapterpriority = ArrayAdapter(App.context,R.layout.support_simple_spinner_dropdown_item,priorityList);


        binding.prioritySpinner2.adapter = arrayAdapterpriority;
        binding.assigneSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                val toast = Toast.makeText(App.context, "Select an Assigne", Toast.LENGTH_SHORT)
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                priority = p2;
            }
        }

        return binding.root;
    }

    fun createNewTask() {
        val title = binding.taskTitle.text.toString();
        val description = binding.description.text.toString();
        val deadline = getDateTimeToSeconds();
        val departmentID = 2;
        val status = 0;

        val newTask = TaskDto(1, title, description, assigneToUser, priority, deadline.toLong(), departmentID, status);

        tasksViewModel.createTask(newTask);
        tasksViewModel.createTaskIsSuccess.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            findNavController().navigate(R.id.action_newTaskFragment_to_listFragment);
        })
    }

    fun getDateTimeToSeconds(): Long {
        try {

            val day = binding.datePicker1.dayOfMonth;
            val month = binding.datePicker1.month;
            val year = binding.datePicker1.year;
            var date = Date(year,month,day);
            return date.time
        } catch (e: Exception) {
            return 0
        }
    }
}