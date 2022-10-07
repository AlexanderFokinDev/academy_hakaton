package pt.amn.moveon.data.models

import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.net.Uri
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import pt.amn.moveon.data.local.AppDatabase
import pt.amn.moveon.data.local.BackupDataJson
import pt.amn.moveon.data.local.toEntityModel
import pt.amn.moveon.data.local.toJsonModel
import pt.amn.moveon.domain.models.BackupSource
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import javax.inject.Inject

class JsonBackupSourceImpl @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val database: AppDatabase
) : BackupSource {

    private var uri: Uri? = null

    fun setUri(exUri: Uri) {
        uri = exUri
    }

    override suspend fun save(): Boolean {

        withContext(Dispatchers.IO) {

            val backupFile = File(appContext.filesDir, "backup_moveon.json")
            backupFile.writeText(getBackupDataInJson(database))

            val uri = FileProvider.getUriForFile(
                appContext, appContext.packageName
                        + ".provider", backupFile
            )

            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, "backup_moveon.json")
                putExtra(Intent.EXTRA_TEXT, "The backup file is in the attachment")
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "text/json"
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            val chooserIntent = createChooser(shareIntent, "Send a backup file").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            appContext.startActivity(chooserIntent, null)
        }

        return true
    }

    private fun getBackupDataInJson(database: AppDatabase): String {

        val backupDataJson = BackupDataJson(
            database.placeDao().getAll().map { entity ->
                entity.toJsonModel()
            },
            database.countryDao().getVisitedCountries().map { entity ->
                entity.toJsonModel()
            }
        )

        return Json.encodeToString(backupDataJson)
    }

    override suspend fun load(): Boolean {

        // Parse Json data to data classes
        var jsonBody = ""
        try {
            val reader =
                BufferedReader(
                    InputStreamReader(
                        appContext.contentResolver.openInputStream(
                            uri ?: return false
                        )
                    )
                )
            jsonBody = reader.readText()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }

        val backupData = Json.decodeFromString<BackupDataJson>(jsonBody)

        loadBackupDataInDatabase(backupData, database)

        return true
    }

    private suspend fun loadBackupDataInDatabase(
        backupData: BackupDataJson,
        database: AppDatabase
    ) {
        // Before let's remove old data
        database.placeDao().deleteAll()
        database.countryDao().removeVisitedFlagForAll()

        // and then load data from the backup
        for (country in backupData.visitedCountries) {
            database.countryDao().setVisitedFlag(country.id)
        }

        for (place in backupData.visitedPlaces) {
            database.placeDao().insert(place.toEntityModel())
        }
    }
}