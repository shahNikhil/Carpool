package nikhil.example.carpool_csis4280

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import nikhil.example.carpool_csis4280.NavigationFragements.*


class HomeActivity : AppCompatActivity() {
    var flag: Boolean = false
    lateinit var profileFragement: Fragment
    lateinit var locationFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val homeFragement = HomeFragment()
        val offerRideFragement = OfferRideFragment()
        val requestRideFragement = RequestRideFragment()
        val registerFragment = RegisterFragment()

        profileFragement = ProfileFragment()
        locationFragment = LocationsFragment()

        flag = intent.extras?.getBoolean("value") ?: false
        if (!flag) {
            displayCurrentFragement(registerFragment)
        } else {
            displayCurrentFragement(homeFragement)
        }
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> displayCurrentFragement(homeFragement)
                R.id.menu_book_ride -> displayCurrentFragement(requestRideFragement)
                R.id.menu_offer_ride -> displayCurrentFragement(offerRideFragement)
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //
        // Handle item selection
        return when (item.getItemId()) {
            R.id.menu_profile -> {
                displayCurrentFragement(profileFragement)
                true
            }
            R.id.menu_location -> {
                displayCurrentFragement(locationFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun displayCurrentFragement(fragement: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, fragement)
            commit()
        }

}