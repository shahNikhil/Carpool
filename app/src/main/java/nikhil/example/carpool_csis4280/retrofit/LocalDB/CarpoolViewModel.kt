package nikhil.example.carpool_csis4280.retrofit.LocalDB

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nikhil.example.carpool_csis4280.retrofit.ConfirmRequestList
import nikhil.example.carpool_csis4280.retrofit.LocationList
import nikhil.example.carpool_csis4280.retrofit.RideList
import nikhil.example.carpool_csis4280.retrofit.UserList

class CarpoolViewModel(app: Application) : AndroidViewModel(app) {
    val database = AppDatabase.getInstance(app)
    var listofUsers = database?.CarpoolAPI()?.getAllUsers()
    var listofRides = database?.CarpoolAPI()?.getAllRide()
    var listofLocation = database?.CarpoolAPI()?.getAllLocation()
    var listofRequest = database?.CarpoolAPI()?.getAllRequest()



    fun insertListOfUsers(mylist: List<UserList>?){
        viewModelScope.launch {
            // Dispatchers.IO means run this in the background
            withContext(Dispatchers.IO) {

                database?.CarpoolAPI()?.insertAllUsers(mylist)
            }
        }
    }
    fun insertListOfRides(mylist: List<RideList>?){
        viewModelScope.launch {
            // Dispatchers.IO means run this in the background
            withContext(Dispatchers.IO) {

                database?.CarpoolAPI()?.insertAllRide(mylist)
            }
        }
    }
    fun insertListOfLocation(mylist: List<LocationList>?){
        viewModelScope.launch {
            // Dispatchers.IO means run this in the background
            withContext(Dispatchers.IO) {

                database?.CarpoolAPI()?.insertAllLocation(mylist)
            }
        }
    }

    fun insertListOfRequest(mylist: List<ConfirmRequestList>?){
        viewModelScope.launch {
            // Dispatchers.IO means run this in the background
            withContext(Dispatchers.IO) {

                database?.CarpoolAPI()?.insertAllRequest(mylist)
            }
        }
    }

    fun checkExistingUser(email: String): Boolean? {
       return database?.CarpoolAPI()?.isRowIsExist(email)
    }
    fun checkCountForRides(email: String): Int? {
        return database?.CarpoolAPI()?.getCountUserId(email)
    }
    fun checkCountForOffers(email: String): Int? {
        return database?.CarpoolAPI()?.getCountDriverId(email)
    }


    fun deleteAllUsers(){
        viewModelScope.launch {
            // Dispatchers.IO means run this in the background
            withContext(Dispatchers.IO) {

                database?.CarpoolAPI()?.deleteAllUsers()
            }
        }
    }

}