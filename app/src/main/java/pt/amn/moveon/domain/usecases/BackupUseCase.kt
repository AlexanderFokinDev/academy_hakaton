package pt.amn.moveon.domain.usecases

import pt.amn.moveon.domain.repositories.BackupRepository

class BackupUseCase(private val repository: BackupRepository) {

    fun getBackupData(): String = repository.getBackupDataInJson()

}