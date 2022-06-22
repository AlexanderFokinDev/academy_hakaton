package pt.amn.moveon.data.repositories

import android.content.Context
import android.net.Uri
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pt.amn.moveon.data.local.AppDatabase
import pt.amn.moveon.data.local.BackupDataJson
import pt.amn.moveon.data.local.toJsonModel
import pt.amn.moveon.domain.repositories.BackupRepository
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

    override suspend fun loadDataFromBackupFile(context: Context, uri: Uri) {

    }
        //withContext(Dispatchers.IO) {

          /*  var jsonBody = ""
            try {
                val reader =
                    BufferedReader(InputStreamReader(context.contentResolver.openInputStream(uri)))
                jsonBody = reader.readText()
            } catch (ex: Exception) {
                ex.printStackTrace()
                return@withContext
            }

            //var backupJson = HashMap<String, List<Any>>()
            val data = Gson().fromJson(jsonBody, HashMap.class)
        }*/
}