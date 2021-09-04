package nikhil.example.carpool_csis4280.retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "LocationList")
data class LocationList(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("Landmark")var Landmark : String,
    @SerializedName("City")var City: String){

}