package nikhil.example.carpool_csis4280.retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "RideList")
data class RideList(

    @SerializedName("SourceLocation")var SourceLocation : String,
    @SerializedName("DestinationLocation")var DestinationLocation: String,
    @SerializedName("Time")var Time: String,
    @SerializedName("Date")var Date : String,
    @SerializedName("seatsCount")var seatsCount: String,
    @SerializedName("isOfferingRide")var isOfferingRide: String,
    @SerializedName("isAskingRide")var isAskingRide: String,
    @SerializedName("userId")var userId: String,
    @PrimaryKey(autoGenerate = false) @SerializedName("rideId")var rideId: String){

}