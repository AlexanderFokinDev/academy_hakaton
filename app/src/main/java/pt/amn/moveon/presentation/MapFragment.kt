package pt.amn.moveon.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import pt.amn.moveon.R
import pt.amn.moveon.databinding.FragmentMapBinding
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.presentation.viewmodels.MapViewModel
import pt.amn.moveon.presentation.viewmodels.utils.LoadStatus
import pt.amn.moveon.utils.AppUtils
import pt.amn.moveon.utils.START_MAP_LATITUDE
import pt.amn.moveon.utils.START_MAP_LONGITUDE
import timber.log.Timber

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private lateinit var googleMapFragment: SupportMapFragment
    private lateinit var myMap: GoogleMap

    private val viewModel: MapViewModel by viewModels()

    private var countries = listOf<Country>()
    private var places = listOf<MoveOnPlace>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!AppUtils.isOnline(context))
            Toast.makeText(requireContext(), "Internet unavailable", Toast.LENGTH_LONG)
                .show()

        googleMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        googleMapFragment.getMapAsync(this@MapFragment)

        viewModel.visitedCountries.observe(viewLifecycleOwner, Observer { resCountries ->
            when (resCountries.status) {
                LoadStatus.SUCCESS -> {
                    setVisitedCountriesFlags(resCountries.data ?: emptyList())
                    countries = resCountries.data ?: emptyList()
                }
                LoadStatus.ERROR -> {
                    Toast.makeText(requireContext(), resCountries.message, Toast.LENGTH_LONG)
                        .show()
                }
                LoadStatus.LOADING -> {
                }
            }
        })

        viewModel.visitedPlaces.observe(viewLifecycleOwner, Observer { resPlaces ->
            when (resPlaces.status) {
                LoadStatus.SUCCESS -> {
                    //setVisitedPlacesFlags(resPlaces.data ?: emptyList())
                    places = resPlaces.data ?: emptyList()
                }
                LoadStatus.ERROR -> {
                    Toast.makeText(requireContext(), resPlaces.message, Toast.LENGTH_LONG)
                        .show()
                }
                LoadStatus.LOADING -> {
                }
            }
        })

        binding.run {
            swMap.setOnCheckedChangeListener {_, isChecked ->
                myMap.clear()

                if (isChecked) {
                    setVisitedPlacesFlags(places)
                } else {
                    setVisitedCountriesFlags(countries)
                }

            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onMapReady(myMap: GoogleMap) {

        myMap.moveCamera(
            CameraUpdateFactory.newLatLng(
                LatLng(
                    START_MAP_LATITUDE,
                    START_MAP_LONGITUDE
                )
            )
        )

    }

    private fun setVisitedCountriesFlags(visitedCountries: List<Country>) {
        for (country in visitedCountries) {
            addCountryMarker(country)
        }
    }

    private fun addCountryMarker(country: Country) {

        try {
            myMap.addMarker(
                MarkerOptions()
                .position(LatLng(country.latitude, country.longitude))
                .title(country.getLocalName())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_green)))
        } catch (ex: Exception) {
            Timber.d("$TAG, Error of adding country at the map $ex")
        }

    }

    private fun setVisitedPlacesFlags(visitedPlaces: List<MoveOnPlace>) {
        for (place in visitedPlaces) {
            addPlaceMarker(place)
        }
    }

    private fun addPlaceMarker(place: MoveOnPlace) {

        try {
            myMap.addMarker(
                MarkerOptions()
                    .position(LatLng(place.latitude, place.longitude))
                    .title(place.name)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_blue)))
        } catch (ex: Exception) {
            Timber.d("$TAG, Error of adding place at the map $ex")
        }

    }

    companion object {
        private const val TAG = "MapFragment"
    }
}