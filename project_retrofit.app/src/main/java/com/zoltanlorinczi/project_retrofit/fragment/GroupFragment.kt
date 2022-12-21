package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentGroupBinding
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.adapter.UserListAdapter
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.UserResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.*
import java.util.Vector

/**
 * Author:  Zoltan Lorinczi
 * Date:    12/2/2021
 */
class GroupFragment : Fragment(R.layout.fragment_group), UserListAdapter.OnItemClickListener,
    UserListAdapter.OnItemLongClickListener {

    companion object {
        private val TAG: String = javaClass.simpleName
    }
    private lateinit var binding: FragmentGroupBinding;
    private lateinit var groupViewModel: GroupViewModel;
    private lateinit var userViewModel: UsersViewModel;
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserListAdapter
    private var currentUsers = ArrayList<UserResponse>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = UsersViewModelFactory(ThreeTrackerRepository())
        val factoryGroup = GroupViewModelFactory(ThreeTrackerRepository())
        userViewModel = ViewModelProvider(requireActivity(), factory)[UsersViewModel::class.java]
        groupViewModel = ViewModelProvider(requireActivity(),factoryGroup)[GroupViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)

        navBar?.visibility = View.VISIBLE;
        binding = FragmentGroupBinding.inflate(inflater);
        recyclerView = binding.recyclerViewGroupUsers;
        setupRecyclerView()

        if(userViewModel.users.value != null){
        for (item in userViewModel.users.value!!) {
            if (item.department_id == groupViewModel.ID){
                currentUsers.add(item)
                Log.i("Users",item.first_name)
            }
        }
        }else{
            val toast = Toast.makeText(App.context, "No User Found", Toast.LENGTH_SHORT)
            toast.show();
        }

        userViewModel.users.observe(viewLifecycleOwner) {
            Log.d(TAG, "Tasks list = $it")
            adapter.setData(currentUsers as ArrayList<UserResponse>)
            adapter.notifyDataSetChanged()
        }

        return binding.root;
    }

    private fun setupRecyclerView() {
        adapter = UserListAdapter(ArrayList(), this.requireContext(), this, this)
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
        userViewModel.currentUser.value = currentUsers[position];
        findNavController().navigate(R.id.myProfileFragment);
    }

    override fun onItemLongClick(position: Int) {
    }

    override fun onResume() {
//        groupViewModel.fetchGroups();
        super.onResume()
    }
}