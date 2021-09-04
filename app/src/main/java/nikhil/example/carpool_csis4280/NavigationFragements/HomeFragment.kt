package nikhil.example.carpool_csis4280.NavigationFragements

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nikhil.example.carpool_csis4280.MainActivity
import nikhil.example.carpool_csis4280.R
import nikhil.example.carpool_csis4280.databinding.FragmentHomeBinding
import nikhil.example.carpool_csis4280.retrofit.*
import nikhil.example.carpool_csis4280.retrofit.LocalDB.CarpoolViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    lateinit var viewModel: CarpoolViewModel
    lateinit var binding: FragmentHomeBinding
    lateinit var auth: FirebaseAuth
    private var a:Int = 0
    private var b:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(CarpoolViewModel::class.java)
        val retrofit = Retrofit.Builder().baseUrl(MainActivity.MY_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
        val service = retrofit.create(BackendAPI::class.java)
        service.getAllRideDetails().enqueue(object : Callback<List<RideList>> {
            override fun onResponse(
                call: Call<List<RideList>>,
                response: Response<List<RideList>>
            ) {
                Log.d("test", "onResponse : ${response.body().toString()}")
                viewModel.insertListOfRides(response.body()!!)
            }

            override fun onFailure(call: Call<List<RideList>>, t: Throwable) {
                Log.d("test", "onFailure")
            }

        })

        val user = Firebase.auth.currentUser
        auth = FirebaseAuth.getInstance()

        a= viewModel.checkCountForOffers(user.email)!!
        b = viewModel.checkCountForRides(user.email)!!

        binding.bookingCount.text = b.toString()
        binding.offerCount.text = a.toString()



        return binding.root

    }




}