package pt.amn.moveon.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import pt.amn.moveon.data.models.CountryEntity
import pt.amn.moveon.common.LogNavigator
import pt.amn.moveon.data.models.ContinentEntity

@HiltWorker
class MoveonDatabaseWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val workerDependency: WorkerDependency
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        loadContinentsFromAsset()
        loadCountriesFromAsset()
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun loadCountriesFromAsset() = try {
        val filename = inputData.getString(KEY_FILENAME_COUNTRIES_ASSET)
        if (filename != null) {
            applicationContext.assets.open(filename).use { inputStream ->
                val countryList = Json.decodeFromStream<List<CountryEntity>>(inputStream)
                workerDependency.database.countryDao().insertAll(countryList)

                LogNavigator.debugMessage("$TAG, Success seeding database, count of countries = ${countryList.size}")
                Result.success()
            }
        } else {
            LogNavigator.debugMessage("$TAG, Error seeding database - no valid filename")
            Result.failure()
        }
    } catch (ex: Exception) {
        LogNavigator.debugMessage("$TAG, Error seeding database $ex")
        Result.failure()
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun loadContinentsFromAsset() = try {
        val filename = inputData.getString(KEY_FILENAME_CONTINENTS_ASSET)
        if (filename != null) {
            applicationContext.assets.open(filename).use { inputStream ->
                val continentsList = Json.decodeFromStream<List<ContinentEntity>>(inputStream)
                workerDependency.database.continentDao().insertAll(continentsList)

                LogNavigator.debugMessage("$TAG, Success seeding database," +
                        " count of continents = ${continentsList.size}")
                Result.success()
            }
        } else {
            LogNavigator.debugMessage("$TAG, Error seeding database - no valid filename")
            Result.failure()
        }
    } catch (ex: Exception) {
        LogNavigator.debugMessage("$TAG, Error seeding database $ex")
        Result.failure()
    }

    companion object {
        private const val TAG = "MoveonDatabaseWorker"
        const val KEY_FILENAME_COUNTRIES_ASSET = "COUNTRIES_DATA_FILENAME"
        const val KEY_FILENAME_CONTINENTS_ASSET = "CONTINENTS_DATA_FILENAME"
    }

}