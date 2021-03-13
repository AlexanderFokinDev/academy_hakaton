package pt.amn.moveon.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
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
import pt.amn.moveon.domain.models.Place
import pt.amn.moveon.presentation.viewmodels.MapViewModel
import pt.amn.moveon.presentation.viewmodels.utils.Status
import pt.amn.moveon.utils.START_MAP_LATITUDE
import pt.amn.moveon.utils.START_MAP_LONGITUDE
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private lateinit var googleMapFragment: SupportMapFragment
    private lateinit var myMap: GoogleMap

    private val viewModel: MapViewModel by viewModels()

    private var countries = listOf<Country>()
    private var places = listOf<Place>()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

        googleMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        googleMapFragment.getMapAsync(this@MapFragment)

        viewModel.visitedCountries.observe(viewLifecycleOwner, Observer { resCountries ->
            when (resCountries.status) {
                Status.SUCCESS -> {
                    setVisitedCountriesFlags(resCountries.data ?: emptyList())
                    countries = resCountries.data ?: emptyList()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), resCountries.message, Toast.LENGTH_LONG)
                        .show()
                }
                Status.LOADING -> {
                }
            }
        })

        viewModel.visitedPlaces.observe(viewLifecycleOwner, Observer { resPlaces ->
            when (resPlaces.status) {
                Status.SUCCESS -> {
                    //setVisitedPlacesFlags(resPlaces.data ?: emptyList())
                    places = resPlaces.data ?: emptyList()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), resPlaces.message, Toast.LENGTH_LONG)
                        .show()
                }
                Status.LOADING -> {
                }
            }
        })

        binding.run {
            swMap.setOnCheckedChangeListener {_, isChecked ->
                myMap.clear()

                if (isChecked) {
                    setVisitedPlacesFlags(places);
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

    override fun onMapReady(googleMap: GoogleMap?) {

        myMap = googleMap as GoogleMap
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

    private fun setVisitedPlacesFlags(visitedPlaces: List<Place>) {
        for (place in visitedPlaces) {
            addPlaceMarker(place)
        }
    }

    private fun addPlaceMarker(place: Place) {

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

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}