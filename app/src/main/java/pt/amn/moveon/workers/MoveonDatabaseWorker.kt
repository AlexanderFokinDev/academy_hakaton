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
import kotlinx.coroutines.coroutineScope
import pt.amn.moveon.data.local.CountryEntity
import pt.amn.moveon.utils.COUNTRIES_DATA_FILENAME
import timber.log.Timber

@HiltWorker
class MoveonDatabaseWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val workerDependency: WorkerDependency
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(COUNTRIES_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val countryType = object : TypeToken<List<CountryEntity>>() {}.type
                    val countryList: List<CountryEntity> = Gson().fromJson(jsonReader, countryType)

                    workerDependency.database.countryDao().insertAll(countryList)

                    Timber.d("$TAG, Success seeding database, count of countries = ${countryList.size}")
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Timber.d("$TAG, Error seeding database $ex")
            Result.failure()
        }

    }

    companion object {
        private const val TAG = "MoveonDatabaseWorker"
    }

}