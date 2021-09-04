package nikhil.example.carpool_csis4280.NavigationFragements

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nikhil.example.carpool_csis4280.MainActivity.Companion.MY_URL
import nikhil.example.carpool_csis4280.databinding.FragmentOfferRideBinding
import nikhil.example.carpool_csis4280.retrofit.BackendAPI
import nikhil.example.carpool_csis4280.retrofit.LocalDB.CarpoolViewModel
import nikhil.example.carpool_csis4280.retrofit.RideList
import nikhil.example.carpool_csis4280.retrofit.UserList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class OfferRideFragment : Fragment() {

    var timePickerDialog: TimePickerDialog? = null
    var editTextTime: EditText? = null
    lateinit var binding: FragmentOfferRideBinding
    lateinit var date: String
    var rideId: Int =10
    lateinit var sourceLocation: String
    lateinit var destinationLocation: String
    lateinit var viewModel: CarpoolViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOfferRideBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CarpoolViewModel::class.java)
        val user = Firebase.auth.currentUser
        //get location list from db to array
        var locationList =arrayOf("Douglas College", "Central City Mall", "Metrotown Mall", "Science World", "Waterfront Station", "YVR Airport", "Stanley Park")
        if(viewModel.listofLocation?.size!! > 0){
            locationList = viewModel.listofLocation!!
        }

        val arrayAdapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, locationList) }
        binding.sourceLocation.adapter = arrayAdapter
        binding.destinationLocation.adapter = arrayAdapter

        binding.sourceLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                sourceLocation = locationList?.get(position) ?: "Douglas College"
                //Toast.makeText(activity, "Selected Location" + " " + locationList[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }



        binding.destinationLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                destinationLocation = locationList?.get(position) ?: "Douglas College"
                //Toast.makeText(activity, "Selected Location" + " " + locationList[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }



        binding.calender.setOnClickListener(View.OnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd =
                activity?.let { it1 ->
                    DatePickerDialog(it1, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        // Display Selected date in Toast
                        //Toast.makeText(activity, """$dayOfMonth - ${monthOfYear + 1} - $year""", Toast.LENGTH_LONG).show()
                        date = "$dayOfMonth/${monthOfYear + 1}/$year"
                        binding.txtDate.setText(date)
                    }, year, month, day)
                }
            dpd?.show()
        })
        binding.btnShowTime.setOnClickListener(View.OnClickListener { openTimeDialog() })
        binding.btnOffer.setOnClickListener(View.OnClickListener {

            //validation
            if(binding.sourceLocation.selectedItem.equals(binding.destinationLocation.selectedItem)){
                Toast.makeText(activity, "Source and Destination Location should be different" , Toast.LENGTH_LONG).show()
            }

            else if(binding.txtPassengerCount.text.isNullOrBlank() || binding.txtPassengerCount.text.isNullOrEmpty()){

                Toast.makeText(activity, "Total Passengers is null!!" , Toast.LENGTH_LONG).show()
            }

            else if(binding.txtPassengerCount.text.toString().toInt() < 1|| binding.txtPassengerCount.text.toString().toInt()> 3){
                Toast.makeText(activity, "Invalid Seat Count, Count should be between 1 to 3" , Toast.LENGTH_LONG).show()
            }
            else if(binding.txtTime.text.toString().trim().isNullOrEmpty()){
                Toast.makeText(activity, "Enter valid time" , Toast.LENGTH_LONG).show()
            }
            else {
                rideId++
                var mylist = listOf(
                    RideList(
                        sourceLocation,
                        destinationLocation,
                        binding.txtTime.text.toString(),
                        binding.txtDate.text.toString(),
                        binding.txtPassengerCount.text.toString(),
                        "true",
                        "false",
                        user.email.toString(),
                        rideId.toString()




                    )
                )
                viewModel.insertListOfRides(mylist)
                addRide(mylist)
            }

            //logic to insert into table should go here
        })

        binding.btnClear.setOnClickListener(View.OnClickListener {
            binding.txtTime.setText("")
            binding.txtPassengerCount.setText("")
            binding.sourceLocation.setSelection(0)
            binding.destinationLocation.setSelection(0)
        })
        return binding.root
    }

    private fun addRide(mylist: List<RideList>) {
        val retrofit = Retrofit.Builder().baseUrl(MY_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
        val service = retrofit.create(BackendAPI::class.java)
        service.addRide(mylist[0].SourceLocation,mylist[0].DestinationLocation,mylist[0].Time,mylist[0].Date,mylist[0].seatsCount,mylist[0].isOfferingRide,mylist[0].isAskingRide,mylist[0].userId,mylist[0].rideId).enqueue(
            object : Callback<List<RideList>>{
                override fun onResponse(
                    call: Call<List<RideList>>,
                    response: Response<List<RideList>>
                ) {
                    Log.d("test", "onResponse added new ride ")
                    Toast.makeText(activity,"Successfully Added Ride",Toast.LENGTH_LONG).show()

                }

                override fun onFailure(call: Call<List<RideList>>, t: Throwable) {
                    Log.d("test", "onFailure to add to new ride")
                }

            })

    }

    private fun openTimeDialog() {
        val hourOfDay = 2
        val minute = 2
        val is24HourView = true
        timePickerDialog = TimePickerDialog(
            activity, android.R.style.Theme_Holo_Light_Dialog,
            TimePickerDialog.OnTimeSetListener { timePicker, i, i1 -> //*Return values
                binding.txtTime.setText("$i:$i1")
            }, hourOfDay, minute, is24HourView
        )
        timePickerDialog!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        timePickerDialog!!.setTitle("Select a Time")
        timePickerDialog!!.show()
    }

}