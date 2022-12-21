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
import com.zoltanlorinczi.project_retorfit.databinding.FragmentMyProfileBinding
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModelFactory

class MyProfileFragment : Fragment() {
    private lateinit var usersViewModel: UsersViewModel;
    private lateinit var binding: FragmentMyProfileBinding;
    var editable:Boolean = false;
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

        setProperties();
        return binding.root
    }

    fun setProperties(){
        binding.firtNameText.text = usersViewModel.currentUser.value?.first_name;
        binding.lastNameText.text = usersViewModel.currentUser.value?.last_name;
        binding.emailText.text = usersViewModel.currentUser.value?.email;
        binding.locationText.text = usersViewModel.currentUser.value?.location;
        binding.phoneText.text = usersViewModel.currentUser.value?.phone_number.toString();
        if(usersViewModel.currentUser.value?.id == usersViewModel.myUser.value?.id){
            binding.editButton.visibility= View.VISIBLE;
        }else{
            binding.editButton.visibility= View.GONE;
        }
        binding.editButton.setOnClickListener{
            edit();
        }
    }

    fun edit(){
        findNavController().navigate(R.id.action_myProfileFragment_to_updateProfile);
    }

}