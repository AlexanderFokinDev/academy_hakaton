package pt.amn.moveon.presentation

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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

    private var isRationaleShown = false
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private val viewModel : SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    viewModel.restoreBackup(requireContext(), result.data)
                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    restoreBackup()
                } else {
                    LogNavigator.toastMessage(
                        requireContext(),
                        R.string.permission_not_granted_text
                    )
                }
            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restorePreferencesData()

        binding.run {
            btCreateBackup.setOnClickListener {
                viewModel.createBackup(requireContext())
            }

            btRestoreBackup.setOnClickListener {
                checkPermissionForRestoreBackup()
            }

            tvVersion.text =
                String.format(getString(R.string.app_version), BuildConfig.VERSION_NAME)
        }
    }

    private fun checkPermissionForRestoreBackup() {

        when {
            readExternalPermissionGranted() -> restoreBackup()

            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) ->
                showReadStoragePermissionExplanationDialog()

            isRationaleShown -> showReadStoragePermissionDeniedDialog()

            else -> requestReadExternalPermission()
        }

    }

    private fun readExternalPermissionGranted(): Boolean {

        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    }

    private fun requestReadExternalPermission() {

        context?.let {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    }

    private fun showReadStoragePermissionExplanationDialog() {
        context?.let { theContext ->
            AlertDialog.Builder(theContext)
                .setMessage(R.string.permission_explanation_read_storage_text)
                .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                    isRationaleShown = true
                    requestReadExternalPermission()
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.dialog_negative_button) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun showReadStoragePermissionDeniedDialog() {
        context?.let { theContext ->
            AlertDialog.Builder(theContext)
                .setMessage(R.string.permission_dialog_denied_read_storage_text)
                .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                    startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + theContext.packageName)
                        )
                    )
                }
                .setNegativeButton(R.string.dialog_negative_button) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun restoreBackup() {

        val intentGetJson = Intent().apply {
            type = "*/json"
            action = Intent.ACTION_GET_CONTENT
        }

        resultLauncher.launch(intentGetJson)
    }

    private fun savePreferencesData() {
        activity?.let { fragmentActivity ->
            fragmentActivity.getPreferences(Context.MODE_PRIVATE).edit()
                .putBoolean(KEY_READ_STORAGE_PERMISSION_RATIONALE_SHOWN, isRationaleShown)
                .apply()
        }
    }

    private fun restorePreferencesData() {
        isRationaleShown = activity?.getPreferences(Context.MODE_PRIVATE)?.getBoolean(
            KEY_READ_STORAGE_PERMISSION_RATIONALE_SHOWN,
            false
        ) ?: false
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.workplaceFragment).isVisible = true
        menu.findItem(R.id.mainmenu_action_back).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onDetach() {
        requestPermissionLauncher.unregister()
        resultLauncher.unregister()
        super.onDetach()
    }

    override fun onDestroyView() {
        _binding = null
        savePreferencesData()
        super.onDestroyView()
    }

    companion object {
        private const val KEY_READ_STORAGE_PERMISSION_RATIONALE_SHOWN =
            "KEY_READ_STORAGE_PERMISSION_RATIONALE_SHOWN"
    }
}