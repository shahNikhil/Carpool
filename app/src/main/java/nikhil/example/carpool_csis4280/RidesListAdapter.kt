package nikhil.example.carpool_csis4280

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nikhil.example.carpool_csis4280.databinding.ListItemBinding
import nikhil.example.carpool_csis4280.retrofit.BackendAPI
import nikhil.example.carpool_csis4280.retrofit.ConfirmRequestList
import nikhil.example.carpool_csis4280.retrofit.LocalDB.CarpoolViewModel
import nikhil.example.carpool_csis4280.retrofit.RideList
import nikhil.example.carpool_csis4280.retrofit.UserList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RidesListAdapter (
    private val rideList: List<RideList>
) :
    RecyclerView.Adapter<RidesListAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = rideList.size

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mylist = rideList[position]

        with(holder.binding) {
            source.text = mylist.SourceLocation
            destination.text = mylist.DestinationLocation
            date.text = mylist.Date
            time.text = mylist.Time

            btnBook.setOnClickListener {

                val user = Firebase.auth.currentUser


                val retrofit = Retrofit.Builder().baseUrl(MainActivity.MY_URL).addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
                val service = retrofit.create(BackendAPI::class.java)
                service.confirmRide(mylist.rideId,mylist.userId,user.email).enqueue(
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
                Navigation.createNavigateOnClickListener(R.id.action_requestRideFragment_to_homeFragement)
            }

        }


    }
}