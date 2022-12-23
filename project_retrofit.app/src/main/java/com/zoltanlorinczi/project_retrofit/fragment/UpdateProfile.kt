
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentUpdateProfileBinding
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.TaskDto
import com.zoltanlorinczi.project_retrofit.api.model.UserUpdateDto
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UsersViewModelFactory

class UpdateProfile : Fragment(R.layout.fragment_update_profile) {
    private lateinit var binding: FragmentUpdateProfileBinding;
    private lateinit var usersViewModel: UsersViewModel;
    private var profileIcon: ImageView? = null
    private var firstNameField: EditText? = null
    private var lastNameField: EditText? = null
    private var emailField: EditText? = null
    private var locationField: EditText? = null
    private var phoneNumberField: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateProfileBinding.inflate(inflater);
        val factory = UsersViewModelFactory(ThreeTrackerRepository());
        usersViewModel = ViewModelProvider(requireActivity(), factory)[UsersViewModel::class.java];

        setProperties();
        return binding.root
    }

    fun setProperties(){
        binding.firstNameTextView.setText(usersViewModel.currentUser.value?.first_name);
        binding.secondNameTextView.setText(usersViewModel.currentUser.value?.last_name);
        binding.emailTextView.setText(usersViewModel.currentUser.value?.email);
        binding.locationTextView.setText(usersViewModel.currentUser.value?.location);
        binding.phoneNumberTextView.setText(usersViewModel.currentUser.value?.phone_number.toString());
        binding.preloader.visibility = View.GONE;
        binding.editButton.setOnClickListener{
            edit();
        }
    }

    fun edit(){

        binding.preloader.visibility  =View.VISIBLE;
        val firstName:String = binding.firstNameTextView.text.toString();
        val secondName:String = binding.secondNameTextView.text.toString();
        val location:String =  binding.locationTextView.text.toString();
        val phoneNumber:String = binding.phoneNumberTextView.text.toString();
        val updateProfileDto =  UserUpdateDto(secondName,firstName,location,phoneNumber,"");


        Handler().postDelayed({
            usersViewModel.updateMyProfile(updateProfileDto);
        },2000);

        usersViewModel.updateProfileIsSuccesful.observe(viewLifecycleOwner, Observer {
                findNavController().navigate(R.id.action_updateProfile_to_myProfileFragment);
        })

    }}
