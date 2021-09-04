package nikhil.example.carpool_csis4280.retrofit.LocalDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nikhil.example.carpool_csis4280.retrofit.ConfirmRequestList
import nikhil.example.carpool_csis4280.retrofit.LocationList
import nikhil.example.carpool_csis4280.retrofit.RideList
import nikhil.example.carpool_csis4280.retrofit.UserList


@Database(entities = [UserList::class,RideList::class,LocationList::class,ConfirmRequestList::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun CarpoolAPI(): CarpoolAPI?


    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "carpool.db"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}
