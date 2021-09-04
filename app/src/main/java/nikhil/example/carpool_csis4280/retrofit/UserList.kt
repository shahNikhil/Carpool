package nikhil.example.carpool_csis4280.retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "UserList")
data class UserList(
                    @PrimaryKey(autoGenerate = false)
                    @SerializedName("email")var email : String,
                    @SerializedName("licenseNumber")var licenseNumber: String,
                    @SerializedName("phoneNumber")var phoneNumber: String){

}