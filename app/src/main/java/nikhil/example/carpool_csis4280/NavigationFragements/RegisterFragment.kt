package nikhil.example.carpool_csis4280.NavigationFragements

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nikhil.example.carpool_csis4280.HomeActivity
import nikhil.example.carpool_csis4280.MainActivity.Companion.MY_URL
import nikhil.example.carpool_csis4280.databinding.FragmentRegisterBinding
import nikhil.example.carpool_csis4280.retrofit.*
import nikhil.example.carpool_csis4280.retrofit.LocalDB.CarpoolViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    lateinit var viewModel: CarpoolViewModel
    private val LNO: TextInputEditText? = null
    private val PNO: TextInputEditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CarpoolViewModel::class.java)

        val user = Firebase.auth.currentUser

        var abc =viewModel.checkExistingUser(user.email)
        if (abc==true){
            Toast.makeText(requireContext(), user.email, Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), HomeActivity::class.java)
            intent.putExtra("value", true)
            startActivity(intent)

        }
        binding.textButton.setOnClickListener {
            abc =viewModel.checkExistingUser(user.email)
            if(abc==false){
                var lNO = binding.txtLicenseNumber
                var pNO = binding.txtPhoneNumber
                var mylist = listOf(
                    UserList(
                        user.email,
                        lNO.text.toString(),
                        pNO.text.toString()
                    )
                )
                viewModel.insertListOfUsers(mylist)
                addNewUser(mylist)
                abc = viewModel.checkExistingUser(user.email)
                if (abc == true) {
                    Toast.makeText(requireContext(), user.email, Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    intent.putExtra("value", true)
                    startActivity(intent)
                }
            }

        }

        return binding.root
    }

    private fun addNewUser(mylist: List<UserList>) {
        val retrofit = Retrofit.Builder().baseUrl(MY_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
        val service = retrofit.create(BackendAPI::class.java)
        service.addUser(mylist[0].email,mylist[0].licenseNumber,mylist[0].phoneNumber).enqueue(
            object : Callback<UserList> {
                override fun onResponse(
                    call: Call<UserList>,
                    response: Response<UserList>
                ) {
                    Log.d("test", "onResponse added new user ")
                }

                override fun onFailure(call: Call<UserList>, t: Throwable) {
                    Log.d("test", "onFailure to add to new user")
                }

            })

    }



}