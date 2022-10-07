package pt.amn.moveon.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.amn.moveon.domain.models.BackupSource
import pt.amn.moveon.domain.repositories.BackupRepository

class RestoreBackupUseCase(
    private val repository: BackupRepository,
    private val source: BackupSource
) {

    suspend fun execute(): Boolean = withContext(Dispatchers.IO) {
        return@withContext repository.loadDataFromBackup(source)
    }
}