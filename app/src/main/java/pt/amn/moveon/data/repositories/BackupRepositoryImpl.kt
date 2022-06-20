package pt.amn.moveon.data.repositories

import com.google.gson.Gson
import kotlinx.coroutines.flow.toList
import pt.amn.moveon.data.local.AppDatabase
import pt.amn.moveon.domain.repositories.BackupRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupRepositoryImpl @Inject constructor(private val database: AppDatabase) :
    BackupRepository {

    override suspend fun getBackupDataInJson(): String {

        val data = HashMap<String, List<Any>>()
        data.put("countries", database.countryDao().getVisitedCountries())
        data.put("places", database.placeDao().getAll())

        val gson = Gson()
        return gson.toJson(data)
    }
}