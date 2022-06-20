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

        /*val countries: MutableMap<String, String> = HashMap()
        config1["component1"] = "url1"
        config1["component2"] = "url1"
        config1["component3"] = "url1"

        val config2: MutableMap<String, String> = HashMap()
        config2["component1"] = "url1"
        config2["component2"] = "url1"
        config2["component3"] = "url1"

        val map: MutableMap<String, Map<String, String>> = HashMap()
        map["config1"] = config1
        map["config2"] = config2

        val data = Data(map)*/

       /* Get gson from data class
        Gson gson = new Gson();
        String json = gson.toJson(data);*/

        val countriesMap: MutableMap<Int, String> = HashMap()
        for (countryEntity in database.countryDao().getVisitedCountries()) {
            countriesMap[countryEntity.id] = countryEntity.nameEn
        }

        /*val placesMap: MutableMap<Int, String> = HashMap()
        for (placeEntity in database.placeDao().getAll()) {
            placesMap[placeEntity.id] = placeEntity.nameEn
        }*/

        val data = HashMap<String, Map<Int, String>>()
        data.put("countries", countriesMap)

        val gson = Gson()
        return gson.toJson(data)
    }
}