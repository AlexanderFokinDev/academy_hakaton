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
import pt.amn.moveon.databinding.FragmentWorkplaceBinding
import pt.amn.moveon.presentation.viewmodels.WorkplaceViewModel
import pt.amn.moveon.presentation.viewmodels.utils.LoadStatus
import pt.amn.moveon.utils.COUNT_COUNTRIES_IN_THE_WORLD

@AndroidEntryPoint
class WorkplaceFragment : Fragment() {

    private var _binding: FragmentWorkplaceBinding? = null
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private val viewModel: WorkplaceViewModel by viewModels()

    private var countVisitedCountries: Int = 1
    private var countVisitedPlaces: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWorkplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateStatistics()

        viewModel.visitedCountries.observe(viewLifecycleOwner, Observer { resCountries ->
            if (resCountries.status == LoadStatus.SUCCESS) {
                countVisitedCountries = resCountries.data?.size ?: 1
                updateStatistics()
            }
        })

        viewModel.visitedPlaces.observe(viewLifecycleOwner, Observer { resPlaces ->
            if (resPlaces.status == LoadStatus.SUCCESS) {
                countVisitedPlaces = resPlaces.data?.size ?: 0
                updateStatistics()
            }
        })

        binding.run {
            btEditCountries.setOnClickListener {
                findNavController().navigate(R.id.action_workplaceFragment_to_countriesFragment)
            }

            mtrlCardStatistics.setOnClickListener {
                findNavController().navigate(R.id.action_workplaceFragment_to_countriesFragment)
            }

            animation.setOnClickListener {
                findNavController().navigate(R.id.action_workplaceFragment_to_countriesFragment)
            }

            animation.playAnimation()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.workplaceFragment).isVisible = false
        menu.findItem(R.id.mainmenu_action_back).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun updateStatistics() {

        val percent_of_the_world = if (countVisitedCountries == 0) 0.0 else {
            countVisitedCountries / (COUNT_COUNTRIES_IN_THE_WORLD / 100.0)
        }
        val level = if(countVisitedCountries < 10) 1 else countVisitedCountries / 10

        binding.run {

            tvCountStatistics.text =
                String.format(getString(R.string.statistics_result1), countVisitedCountries)

            tvPercentStatistics.text =
                String.format(getString(R.string.statistics_result2), percent_of_the_world)

            tvLevel.text = String.format(getString(R.string.statistics_level), level)
            progressBarLevel.progress = level

            tvPlacesStatistics.text = String.format(getString(R.string.statistics_result3), countVisitedPlaces)

            animation.playAnimation()

        }

    }
}