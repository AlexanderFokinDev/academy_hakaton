package pt.amn.moveon.data.repositories

import android.content.Context
import android.net.Uri
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pt.amn.moveon.data.local.AppDatabase
import pt.amn.moveon.data.local.BackupDataJson
import pt.amn.moveon.data.local.toEntityModel
import pt.amn.moveon.data.local.toJsonModel
import pt.amn.moveon.domain.repositories.BackupRepository
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupRepositoryImpl @Inject constructor(private val database: AppDatabase) :
    BackupRepository {

    override suspend fun getBackupDataInJson(): String {

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

    override suspend fun loadDataFromBackupFile(context: Context, uri: Uri) : Boolean {

            // Parse Json data to data classes
            var jsonBody = ""
            try {
                val reader =
                    BufferedReader(InputStreamReader(context.contentResolver.openInputStream(uri)))
                jsonBody = reader.readText()
            } catch (ex: Exception) {
                ex.printStackTrace()
                return false
            }

            val backupData = Json.decodeFromString<BackupDataJson>(jsonBody)

            loadBackupDataInDatabase(backupData)
            return true
        }

    private suspend fun loadBackupDataInDatabase(backupData: BackupDataJson) {
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