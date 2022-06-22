package pt.amn.moveon.domain.usecases

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.amn.moveon.domain.repositories.BackupRepository
import java.io.File

class BackupUseCase(private val repository: BackupRepository) {

    private suspend fun getBackupData(): String = withContext(Dispatchers.IO) {
        repository.getBackupDataInJson()
    }

    suspend fun sendBackup(context: Context) = withContext(Dispatchers.IO) {

        val backupFile = File(context.filesDir, "backup_moveon.json")
        backupFile.writeText(getBackupData())
        val uri = FileProvider.getUriForFile(context, context.applicationContext.packageName
        + ".provider", backupFile)

        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, "backup_moveon.json")
            putExtra(Intent.EXTRA_TEXT, "The backup file is in the attachment")
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "text/json"
        }
        startActivity(context, Intent.createChooser(shareIntent, "Send a backup file"), null)
    }

    suspend fun restoreBackup(context: Context, uri: Uri) = withContext(Dispatchers.IO) {
        repository.loadDataFromBackupFile(context, uri)
    }

}