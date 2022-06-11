package pt.amn.moveon.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import pt.amn.moveon.BuildConfig
import pt.amn.moveon.R
import pt.amn.moveon.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

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
                Toast.makeText(requireContext(), R.string.bt_create_backup, Toast.LENGTH_LONG)
                    .show()
            }
            btRestoreBackup.setOnClickListener {
                Toast.makeText(requireContext(), R.string.bt_restore_backup, Toast.LENGTH_LONG)
                    .show()
            }

            tvVersion.text =
                String.format(getString(R.string.app_version), BuildConfig.VERSION_NAME)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}