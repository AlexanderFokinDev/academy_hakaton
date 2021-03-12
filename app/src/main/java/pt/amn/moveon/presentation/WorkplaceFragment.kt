package pt.amn.moveon.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import pt.amn.moveon.R
import pt.amn.moveon.databinding.FragmentWorkplaceBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorkplaceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkplaceFragment : Fragment() {

    private var _binding: FragmentWorkplaceBinding? = null
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.workplaceFragment).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun updateStatistics() {

        val percent_of_the_world = 15.33
        val level = 3
        val countVisitedCountries = 30

        binding.run {
            btEditCountries.setOnClickListener {
                findNavController().navigate(R.id.action_workplaceFragment_to_countriesFragment)
            }

            // TODO: fill statistics information
            tvCountStatistics.text =
                String.format(getString(R.string.statistics_result1), countVisitedCountries)

            tvPercentStatistics.text =
                String.format(getString(R.string.statistics_result2), percent_of_the_world)

            tvLevel.text = String.format(getString(R.string.statistics_level), level)
            progressBarLevel.progress = level

            tvPlacesStatistics.text = String.format(getString(R.string.statistics_result3), 179)

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WorkplaceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkplaceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}