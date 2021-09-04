package nikhil.example.carpool_csis4280.retrofit.LocalDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nikhil.example.carpool_csis4280.retrofit.ConfirmRequestList
import nikhil.example.carpool_csis4280.retrofit.LocationList
import nikhil.example.carpool_csis4280.retrofit.RideList
import nikhil.example.carpool_csis4280.retrofit.UserList

@Dao
interface CarpoolAPI {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // it performs an update if it exists
    fun insertAllUsers(UserList: List<UserList>?)

    @Query("SELECT * FROM UserList ORDER BY email ASC")
    fun getAllUsers(): LiveData<List<UserList>>

    @Query("SELECT EXISTS(SELECT * FROM UserList WHERE email = :email)")
    fun isRowIsExist(email : String) : Boolean

    @Query("DELETE FROM UserList")
    fun deleteAllUsers():Int

    @Insert(onConflict = OnConflictStrategy.REPLACE) // it performs an update if it exists
    fun insertAllRide(RideList: List<RideList>?)

    @Query("SELECT * FROM RideList ORDER BY rideId ASC")
    fun getAllRide(): LiveData<List<RideList>>

    @Query("DELETE FROM RideList")
    fun deleteAllRide():Int

    @Insert(onConflict = OnConflictStrategy.REPLACE) // it performs an update if it exists
    fun insertAllLocation(RideList: List<LocationList>?)

    @Query("SELECT * FROM LocationList ORDER BY Landmark ASC")
    fun getAllLocationData(): LiveData<List<LocationList>>

    @Query("DELETE FROM LocationList")
    fun deleteAllLocation():Int

    @Insert(onConflict = OnConflictStrategy.REPLACE) // it performs an update if it exists
    fun insertAllRequest(RideList: List<ConfirmRequestList>?)

    @Query("SELECT * FROM ConfirmRequestList ORDER BY rideId ASC")
    fun getAllRequest(): LiveData<List<ConfirmRequestList>>

    @Query("SELECT Landmark FROM LocationList ORDER BY Landmark ASC")
    fun getAllLocation(): Array<String>

    @Query("DELETE FROM ConfirmRequestList")
    fun deleteAllRequest():Int

    @Query("SELECT COUNT(*) FROM ConfirmRequestList Where userId =  :email")
    fun getCountUserId(email: String):Int

    @Query("SELECT COUNT(*) FROM ConfirmRequestList Where driverId =  :email")
    fun getCountDriverId(email: String):Int
}