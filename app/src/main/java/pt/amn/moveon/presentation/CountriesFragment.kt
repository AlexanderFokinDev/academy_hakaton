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
import pt.amn.moveon.presentation.viewmodels.utils.LoadStatus

@AndroidEntryPoint
class CountriesFragment : Fragment() {

    private var _binding: FragmentCountriesBinding? = null
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private val viewModel: CountriesViewModel by viewModels()

    private lateinit var adapter: CountriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                LoadStatus.SUCCESS -> {
                    updateData(resCountries.data ?: emptyList())
                }
                LoadStatus.ERROR -> {
                    Toast.makeText(requireContext(), resCountries.message, Toast.LENGTH_LONG)
                        .show()
                }
                LoadStatus.LOADING -> {
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
            val arguments = Bundle().apply {
                putParcelable(ARG_COUNTRY, country)
            }
            findNavController().navigate(R.id.action_countriesFragment_to_countryFragment, arguments)
        }

        override fun onVisitedCheck(country: Country, visited: Boolean) {
            viewModel.changeVisitedFlagOfCountry(country, visited)
        }

    }
}