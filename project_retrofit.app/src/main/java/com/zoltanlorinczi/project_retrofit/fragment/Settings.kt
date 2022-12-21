package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentSettingsBinding
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager.Companion.KEY_TOKEN
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModelFactory


class Settings : Fragment() {

    lateinit var binding: FragmentSettingsBinding;
    lateinit var usersViewModel: UsersViewModel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater);
        val factory = UsersViewModelFactory(ThreeTrackerRepository());
        usersViewModel = ViewModelProvider(requireActivity(), factory)[UsersViewModel::class.java];

        binding.logOut.setOnClickListener{
            logOut();
        }
        binding.toProfileBtn.setOnClickListener{
            usersViewModel.currentUser.value = usersViewModel.myUser.value;
            findNavController().navigate(R.id.action_settings_to_myProfileFragment)
        }
        return binding.root;
    }

    fun logOut(){
        App.sharedPreferences.putStringValue(KEY_TOKEN,"NULL");
        findNavController().navigate(R.id.loginFragment);
    }


}