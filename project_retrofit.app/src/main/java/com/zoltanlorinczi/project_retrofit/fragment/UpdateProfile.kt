
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
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
        binding.editButton.setOnClickListener{
            edit();
        }
    }

    fun edit(){
        val firstName:String = binding.firstNameTextView.text.toString();
        val secondName:String = binding.secondNameTextView.text.toString();
        val email:String = binding.emailTextView.text.toString();
        val location:String =  binding.locationTextView.text.toString();
        val phoneNumber:Int = binding.phoneNumberTextView.text.toString().toInt();

        val updateProfileDto =  UserUpdateDto(2, secondName,firstName, email, location, phoneNumber,0,0,null);

        usersViewModel.updateMyProfile(updateProfileDto);
        findNavController().navigate(R.id.action_updateProfile_to_myProfileFragment);
    }

}