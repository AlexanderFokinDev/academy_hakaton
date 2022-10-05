package pt.amn.moveon.presentation.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.amn.moveon.R
import pt.amn.moveon.common.LogNavigator
import pt.amn.moveon.domain.repositories.BackupRepository
import pt.amn.moveon.domain.usecases.RestoreBackupUseCase
import pt.amn.moveon.domain.usecases.SaveBackupUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(repository: BackupRepository)
    : ViewModel() {

    private val saveUseCase = SaveBackupUseCase(repository)
    private val restoreUseCase = RestoreBackupUseCase(repository)

    fun createBackup(context: Context) {
        viewModelScope.launch {
            saveUseCase.execute(context)
        }
    }

    fun restoreBackup(context: Context, intentData: Intent?) {

        loadDataFromExternalFile(intentData, context)

    }

    private fun loadDataFromExternalFile(intentData: Intent?, context: Context) {

        val uri = intentData?.data

        if (uri != null) {
            viewModelScope.launch {
                if (restoreUseCase.execute(context, uri)) {
                    LogNavigator.toastMessage(context, R.string.message_restore_backup_success)
                }
            }
        } else {
            // TODO: an error message
        }
    }



}