package pt.amn.moveon.data.local
import android.content.Context
import com.google.gson.stream.JsonReader
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.firstOrNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import pt.amn.moveon.common.COUNTRIES_DATA_FILENAME
import pt.amn.moveon.common.COUNT_COUNTRIES_IN_THE_WORLD

@RunWith(AndroidJUnit4::class)
class CountryDaoTest {

    private lateinit var countryDao: CountryDao
    private lateinit var db: AppDatabase
    private lateinit var scope: CoroutineScope

    @Before
    fun createDb() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        db = AppDatabase.getInstance(context)

        countryDao = db.countryDao()

        scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    }

    @Test
    fun fileForSeedingIsExistAndCorrect() {

        val context = ApplicationProvider.getApplicationContext<Context>()

            context.assets.open(COUNTRIES_DATA_FILENAME).use { inputStream ->
               JsonReader(inputStream.reader()).use { jsonReader ->
                    val countryType = object : TypeToken<List<CountryEntity>>() {}.type
                    val countryList: List<CountryEntity> =
                        Gson().fromJson(jsonReader, countryType)
                   assertTrue(countryList.isNotEmpty())
                }
            }
    }

    @Test
    fun operationCRUDCorrected() {
        val country1 = CountryEntity(1001, "Test1001", "Тест1001", 0.0, 0.0)
        val country2 = CountryEntity(1002, "Test1002", "Тест1002", 0.0, 0.0)
        val country3 = CountryEntity(1003, "Test1003", "Тест1003", 0.0, 0.0)
        val countries = listOf<CountryEntity>(country1, country2, country3)

        scope.launch {
            countryDao.removeVisitedFlagForAll()
            assertEquals(0, countryDao.getVisitedCountries().size)
            countryDao.getAllFlow().firstOrNull()?.map { entity ->
                countryDao.setVisitedFlag(entity.id)
            }
            assertEquals(COUNT_COUNTRIES_IN_THE_WORLD, countryDao.getVisitedCountries().size)
        }
    }

    @After
    fun closeDb() {
        db.close()
        scope.cancel()
    }
}