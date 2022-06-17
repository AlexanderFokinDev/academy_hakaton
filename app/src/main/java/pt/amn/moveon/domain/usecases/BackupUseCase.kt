package pt.amn.moveon.domain.usecases

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import pt.amn.moveon.domain.repositories.BackupRepository

class BackupUseCase(private val repository: BackupRepository) {

    fun getBackupData(): String = repository.getBackupDataInJson()

    fun sendBackup(context: Context) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, getBackupData())
            type = "text/json"
        }
        startActivity(context, Intent.createChooser(shareIntent, null), null)
    }

}