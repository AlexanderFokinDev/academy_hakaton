package pt.amn.moveon.domain.repositories

import android.content.Context
import android.net.Uri

interface BackupRepository {

    suspend fun getBackupDataInJson(): String

    suspend fun loadDataFromBackupFile(context: Context, uri: Uri) : Boolean

}