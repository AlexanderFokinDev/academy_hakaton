package pt.amn.moveon.data.repositories

import com.google.gson.Gson
import pt.amn.moveon.domain.repositories.BackupRepository

class BackupRepositoryImpl : BackupRepository {

    override fun getBackupDataInJson(): String {

        val data = HashMap<String, String>()
        data.put("Uruguay", "WasThere")

        val gson = Gson()
        return gson.toJson(data)
    }
}