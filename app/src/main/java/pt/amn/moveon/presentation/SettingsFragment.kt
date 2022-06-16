package pt.amn.moveon.presentation

import android.app.ProgressDialog.show
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import pt.amn.moveon.BuildConfig
import pt.amn.moveon.R
import pt.amn.moveon.common.LogNavigator
import pt.amn.moveon.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

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

        binding.run {
            btCreateBackup.setOnClickListener {
                LogNavigator.toastMessage(requireContext(), R.string.bt_create_backup)
            }
            btRestoreBackup.setOnClickListener {
                LogNavigator.toastMessage(requireContext(), R.string.bt_restore_backup)
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