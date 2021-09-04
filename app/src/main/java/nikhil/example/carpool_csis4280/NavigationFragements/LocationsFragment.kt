package nikhil.example.carpool_csis4280.NavigationFragements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import nikhil.example.carpool_csis4280.R
import nikhil.example.carpool_csis4280.retrofit.LocalDB.CarpoolViewModel

class LocationsFragment : Fragment() {
    lateinit var viewModel: CarpoolViewModel
    private var myLongitude: Double? = null
    private var myLatitude: Double? = null

    private val callback = OnMapReadyCallback { googleMap ->

        val vancouver = LatLng(49.24966, -123.11934)
        val YVRAirport = LatLng(49.1966913, -123.18151230000001)
        val stanleyPark = LatLng(49.301705, -123.1417)
        val metrotown = LatLng(49.227304, -122.999984)

        val boundsBuilder = LatLngBounds.Builder()
        boundsBuilder.include(vancouver)
        boundsBuilder.include(YVRAirport)
        boundsBuilder.include(stanleyPark)
        boundsBuilder.include(metrotown)

        googleMap.addMarker(MarkerOptions().position(vancouver).title("Vancouver"))
        googleMap.addMarker(MarkerOptions().position(YVRAirport).title("YVRAirport"))
        googleMap.addMarker(MarkerOptions().position(stanleyPark).title("Stanley Park"))
        googleMap.addMarker(MarkerOptions().position(metrotown).title("Metrotown Mall"))

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 1000, 1000, 0))

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(metrotown))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CarpoolViewModel::class.java)

        return inflater.inflate(R.layout.fragment_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}