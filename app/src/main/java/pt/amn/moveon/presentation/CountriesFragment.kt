package pt.amn.moveon.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pt.amn.moveon.R
import pt.amn.moveon.databinding.FragmentCountriesBinding
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.presentation.adapters.CountriesAdapter
import pt.amn.moveon.presentation.adapters.OnRecyclerCountriesClicked
import pt.amn.moveon.presentation.viewmodels.CountriesViewModel
import pt.amn.moveon.presentation.viewmodels.utils.Status

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CountriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CountriesFragment : Fragment() {

    private var _binding: FragmentCountriesBinding? = null
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private val viewModel: CountriesViewModel by viewModels()

    private lateinit var adapter: CountriesAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        adapter = CountriesAdapter(requireContext(), recyclerListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            rvCountries.adapter = adapter
        }

        viewModel.countriesList.observe(viewLifecycleOwner, Observer { resCountries ->
            when (resCountries.status) {
                Status.SUCCESS -> {
                    // load the list of actors
                    updateData(resCountries.data ?: emptyList())
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), resCountries.message, Toast.LENGTH_LONG)
                        .show()
                }
                Status.LOADING -> {
                }
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun updateData(countriesList: List<Country>) {
        adapter.bindCountries(countriesList)
        adapter.notifyDataSetChanged()
    }

    private val recyclerListener = object : OnRecyclerCountriesClicked {
        override fun onCountryClick(country: Country) {
            findNavController().navigate(R.id.action_countriesFragment_to_countryFragment)
        }

        override fun onVisitedCheck(country: Country, visited: Boolean) {
            viewModel.changeVisitedFlagOfCountry(country, visited)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CountriesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CountriesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}