package pt.amn.moveon.domain.usecases

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.amn.moveon.domain.repositories.BackupRepository
import java.io.File

class SaveBackupUseCase(private val repository: BackupRepository) {

    suspend fun execute(context: Context) = withContext(Dispatchers.IO) {

        val backupFile = File(context.filesDir, "backup_moveon.json")
        backupFile.writeText(repository.getBackupDataInJson())
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

}