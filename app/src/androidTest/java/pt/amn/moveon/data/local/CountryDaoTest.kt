package pt.amn.moveon.data.local
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import pt.amn.moveon.common.COUNTRIES_DATA_FILENAME
import pt.amn.moveon.common.COUNT_COUNTRIES_IN_THE_WORLD
import pt.amn.moveon.data.models.CountryEntity

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

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun fileForSeedingIsExistAndCorrect() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        context.assets.open(COUNTRIES_DATA_FILENAME).use { inputStream ->
            val countryList = Json.decodeFromStream<List<CountryEntity>>(inputStream)
            assertTrue(countryList.isNotEmpty())
        }
    }

    @Test
    fun operationCRUDCorrected() {
        val country1 = CountryEntity(1001, "Test1001", "Тест1001", 0.0, 0.0, continentId = 2)
        val country2 = CountryEntity(1002, "Test1002", "Тест1002", 0.0, 0.0, continentId = 4)
        val country3 = CountryEntity(1003, "Test1003", "Тест1003", 0.0, 0.0, continentId = 5)
        val countries = listOf<CountryEntity>(country1, country2, country3)

        scope.launch {
            countryDao.removeVisitedFlagForAll()
            assertEquals(0, countryDao.getVisitedCountries().size)
            countryDao.getAllFlow().firstOrNull()?.map { entity ->
                countryDao.setVisitedFlag(entity.countryEntity.id)
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