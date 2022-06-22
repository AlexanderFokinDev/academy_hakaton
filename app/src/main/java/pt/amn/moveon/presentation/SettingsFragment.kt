package pt.amn.moveon.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pt.amn.moveon.BuildConfig
import pt.amn.moveon.R
import pt.amn.moveon.common.LogNavigator
import pt.amn.moveon.databinding.FragmentSettingsBinding
import pt.amn.moveon.presentation.viewmodels.SettingsViewModel

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private val viewModel : SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intentGetJson = Intent().apply {
            type = "*/json"
            action = Intent.ACTION_GET_CONTENT
        }

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    viewModel.restoreBackup(requireContext(), result.data)
                }
            }

        binding.run {
            btCreateBackup.setOnClickListener {
                viewModel.createBackup(requireContext())
            }

            btRestoreBackup.setOnClickListener {
                resultLauncher.launch(intentGetJson)
            }

            tvVersion.text =
                String.format(getString(R.string.app_version), BuildConfig.VERSION_NAME)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.workplaceFragment).isVisible = true
        menu.findItem(R.id.mainmenu_action_back).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}