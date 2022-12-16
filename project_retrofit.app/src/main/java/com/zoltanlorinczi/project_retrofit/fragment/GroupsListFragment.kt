package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentGroupsListBinding
import com.zoltanlorinczi.project_retorfit.databinding.FragmentTasksListBinding
import com.zoltanlorinczi.project_retorfit.databinding.GroupsListItemBinding
import com.zoltanlorinczi.project_retrofit.adapter.GroupsListAdapter
import com.zoltanlorinczi.project_retrofit.adapter.TasksListAdapter
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.GroupResponse
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.GroupViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.GroupViewModelFactory
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModelFactory

/**
 * Author:  Zoltan Lorinczi
 * Date:    12/2/2021
 */
class GroupsListFragment : Fragment(R.layout.fragment_groups_list), GroupsListAdapter.OnItemClickListener,
    GroupsListAdapter.OnItemLongClickListener {

    companion object {
        private val TAG: String = javaClass.simpleName
    }
    private lateinit var binding: FragmentGroupsListBinding;
    private lateinit var groupViewModel: GroupViewModel;
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GroupsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = GroupViewModelFactory(ThreeTrackerRepository())
        groupViewModel = ViewModelProvider(requireActivity(), factory)[GroupViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)

        navBar?.visibility = View.VISIBLE;
        binding = FragmentGroupsListBinding.inflate(inflater);
        recyclerView = binding.recyclerViewGroup;
        setupRecyclerView()

        groupViewModel.groups.observe(viewLifecycleOwner) {
            Log.d(TAG, "Tasks list = $it")
            adapter.setData(groupViewModel.groups.value as ArrayList<GroupResponse>)
            adapter.notifyDataSetChanged()
        }

        return binding.root;
    }

    private fun setupRecyclerView() {
        adapter = GroupsListAdapter(ArrayList(), this.requireContext(), this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {

        groupViewModel.ID = position;
        findNavController().navigate(R.id.groupFragment);
    }

    override fun onItemLongClick(position: Int) {
    }

    override fun onResume() {
        groupViewModel.fetchGroups();
        super.onResume()
    }
}