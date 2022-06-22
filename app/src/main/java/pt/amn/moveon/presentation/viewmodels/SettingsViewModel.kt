package pt.amn.moveon.presentation.viewmodels

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.amn.moveon.common.LogNavigator
import pt.amn.moveon.domain.repositories.BackupRepository
import pt.amn.moveon.domain.usecases.BackupUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: BackupRepository)
    : ViewModel() {

    private val interactor = BackupUseCase(repository)

    fun createBackup(context: Context) {
        viewModelScope.launch {
            interactor.sendBackup(context)
        }
    }

    fun restoreBackup(context: Context, intentData: Intent?) {

        val uri = intentData?.data

        if (uri != null) {
            viewModelScope.launch {
                interactor.restoreBackup(context, uri)
            }
        } else
        {
            // TODO: an error message
        }
    }
}