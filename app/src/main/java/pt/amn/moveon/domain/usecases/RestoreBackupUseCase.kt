package pt.amn.moveon.domain.usecases

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.amn.moveon.domain.repositories.BackupRepository

class RestoreBackupUseCase(private val repository: BackupRepository) {

    suspend fun execute(context: Context, uri: Uri) : Boolean = withContext(Dispatchers.IO) {
        return@withContext repository.loadDataFromBackupFile(context, uri)
    }
}