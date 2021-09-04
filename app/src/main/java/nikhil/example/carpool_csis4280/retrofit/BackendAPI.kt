package nikhil.example.carpool_csis4280.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface BackendAPI {
    @GET("getalluser")
    fun getAllUser(): Call<List<UserList>>

    @GET("getuser")
    fun getUser(@Query("email") email: String): Call<List<UserList>>


    @POST("adduser")
    fun addUser(
        @Query("email") email: String,
        @Query("licenseNumber")licenseNumber:String,
        @Query("phoneNumber")phoneNumber:String
    ):Call<UserList>

    @GET("getalllocation")
    fun getAllLocation(): Call<List<LocationList>>

    @GET("getlocation")
    fun getLocation(@Query("Landmark") Landmark: String): Call<List<LocationList>>

    @GET("getallridedetails")
    fun getAllRideDetails(): Call<List<RideList>>

    @GET("getridedetails")
    fun getRideDetails(@Query("userId")userId: String): Call<List<RideList>>

    @GET("getallrequest")
    fun getAllRequest(): Call<List<ConfirmRequestList>>

    @GET("getrequest")
    fun getRequest(@Query("rideId")rideId: String): Call<List<ConfirmRequestList>>

    @POST("confirmRide")
    fun confirmRide(
        @Query("rideId")rideId:String,
        @Query("userId")userId:String,
        @Query("driverId") driverId: String
    ):Call<UserList>

    @POST("addride")
    fun addRide(
        @Query("SourceLocation")SourceLocation : String,
        @Query("DestinationLocation")DestinationLocation: String,
        @Query("Time")Time: String,
        @Query("Date")Date : String,
        @Query("seatsCount")seatsCount: String,
        @Query("isOfferingRide")isOfferingRide: String,
        @Query("isAskingRide")isAskingRide: String,
        @Query("userId")userId: String,
        @Query("rideId") rideId: String
    ):Call<List<RideList>>

}