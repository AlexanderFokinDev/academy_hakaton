package pt.amn.moveon.data.repositories

import com.google.gson.Gson
import pt.amn.moveon.domain.repositories.BackupRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupRepositoryImpl @Inject constructor(): BackupRepository {

    override fun getBackupDataInJson(): String {

        /*val config1: MutableMap<String, String> = HashMap()
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

        val data = HashMap<String, String>()
        data.put("Uruguay", "WasThere")

        val gson = Gson()
        return gson.toJson(data)
    }
}