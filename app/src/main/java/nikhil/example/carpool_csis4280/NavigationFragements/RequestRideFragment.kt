package nikhil.example.carpool_csis4280.NavigationFragements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import nikhil.example.carpool_csis4280.R
import nikhil.example.carpool_csis4280.RidesListAdapter
import nikhil.example.carpool_csis4280.databinding.FragmentRequestRideBinding
import nikhil.example.carpool_csis4280.retrofit.LocalDB.CarpoolViewModel

class RequestRideFragment : Fragment() {
    lateinit var binding: FragmentRequestRideBinding
    lateinit var viewModel: CarpoolViewModel
    private lateinit var adapter: RidesListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRequestRideBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CarpoolViewModel::class.java)
        // Inflate the layout for this fragment

        viewModel.listofRides?.observe(viewLifecycleOwner, {
            adapter = RidesListAdapter(it)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        })




        return binding.root
    }


}