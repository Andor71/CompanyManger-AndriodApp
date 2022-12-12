package com.zoltanlorinczi.project_retrofit.fragment


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModelFactory
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModelFactory


class SplashScreen : Fragment() {

    private lateinit var usersViewModel: UsersViewModel;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val factory = UsersViewModelFactory(ThreeTrackerRepository())
        usersViewModel = ViewModelProvider(requireActivity(), factory)[UsersViewModel::class.java]
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)

        navBar?.visibility = View.GONE;
        Handler().postDelayed({
            usersViewModel.getMyUser();
        },3000);
        usersViewModel.currentUser.observe(viewLifecycleOwner, Observer{
            if(it == null){
                findNavController().navigate(R.id.loginFragment);
            }else{
                findNavController().navigate(R.id.listFragment);
            }
        });


        return inflater.inflate(R.layout.fragment_splash_screen, container, false)

    }

}
