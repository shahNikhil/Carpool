package nikhil.example.carpool_csis4280.retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "ConfirmRequestList")
data class ConfirmRequestList(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("rideId") var rideId: Int,
    @SerializedName("userId") var userId: String,
    @SerializedName("driverId") var driverId: String
) {

}