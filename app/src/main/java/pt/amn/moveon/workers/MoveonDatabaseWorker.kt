package pt.amn.moveon.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.amn.moveon.data.local.CountryEntity
import pt.amn.moveon.common.LogNavigator

@HiltWorker
class MoveonDatabaseWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val workerDependency: WorkerDependency
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val countryType = object : TypeToken<List<CountryEntity>>() {}.type
                        val countryList: List<CountryEntity> =
                            Gson().fromJson(jsonReader, countryType)

                        workerDependency.database.countryDao().insertAll(countryList)

                        LogNavigator.debugMessage("$TAG, Success seeding database, count of countries = ${countryList.size}")
                        Result.success()
                    }
                }
            } else
            {
                LogNavigator.debugMessage("$TAG, Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            LogNavigator.debugMessage("$TAG, Error seeding database $ex")
            Result.failure()
        }

    }

    companion object {
        private const val TAG = "MoveonDatabaseWorker"
        const val KEY_FILENAME = "COUNTRIES_DATA_FILENAME"
    }

}