package pt.amn.moveon.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
}