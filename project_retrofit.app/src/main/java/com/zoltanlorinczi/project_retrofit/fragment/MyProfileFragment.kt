package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentMyProfileBinding
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModelFactory

class MyProfileFragment : Fragment() {
    private lateinit var usersViewModel: UsersViewModel;
    private lateinit var binding: FragmentMyProfileBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val factory = UsersViewModelFactory(ThreeTrackerRepository())
        usersViewModel = ViewModelProvider(requireActivity(), factory)[UsersViewModel::class.java]

        binding = FragmentMyProfileBinding.inflate(inflater)
        return binding.root
    }

    fun setProperties(){
        binding.nameLayout.text

    }
}