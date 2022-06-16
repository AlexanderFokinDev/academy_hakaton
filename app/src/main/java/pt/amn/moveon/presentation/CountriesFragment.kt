package pt.amn.moveon.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pt.amn.moveon.R
import pt.amn.moveon.common.LogNavigator
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
        setHasOptionsMenu(true)
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
                    LogNavigator.toastMessage(requireContext(), resCountries.message)
                }
                LoadStatus.LOADING -> {
                }
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.workplaceFragment).isVisible = false
        menu.findItem(R.id.mainmenu_action_back).isVisible = true
        super.onPrepareOptionsMenu(menu)
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