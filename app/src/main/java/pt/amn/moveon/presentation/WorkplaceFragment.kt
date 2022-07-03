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
import kotlinx.coroutines.*
import pt.amn.moveon.R
import pt.amn.moveon.databinding.FragmentWorkplaceBinding
import pt.amn.moveon.presentation.viewmodels.WorkplaceViewModel
import pt.amn.moveon.common.LogNavigator

@AndroidEntryPoint
class WorkplaceFragment : Fragment() {

    private var _binding: FragmentWorkplaceBinding? = null
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private val viewModel: WorkplaceViewModel by viewModels()

    private val handlerException = CoroutineExceptionHandler { _, throwable ->
        LogNavigator.debugMessage("${TAG}, exception handled ${throwable.message}")
    }
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main + handlerException)

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

        viewModel.visitedCountries.observe(viewLifecycleOwner) { resCountries ->
            viewModel.countVisitedCountries = resCountries.size
            updateStatistics()
        }

        viewModel.visitedPlaces.observe(viewLifecycleOwner, Observer { resPlaces ->
            viewModel.countVisitedPlaces = resPlaces.size
            updateStatistics()
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

            scope.launch {
                playAnimation()
            }
        }
    }

    private suspend fun playAnimation() = withContext(Dispatchers.Main) {
        binding.animation.playAnimation()
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

        val fullStatistics =
            viewModel.fullStatistics.convertToStatisticsOfTravelerText(context ?: return)

        binding.run {

            tvCountStatistics.text = fullStatistics.countriesText
            tvPercentStatistics.text = fullStatistics.percentText
            tvLevel.text = fullStatistics.levelText
            tvPlacesStatistics.text = fullStatistics.placesText
            progressBarLevel.progress = fullStatistics.progress

            scope.launch {
                playAnimation()
            }

        }

    }

    companion object {
        private const val TAG = "WorkplaceFragment"
    }

}

