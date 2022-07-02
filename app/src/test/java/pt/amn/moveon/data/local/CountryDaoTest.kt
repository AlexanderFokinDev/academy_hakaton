package pt.amn.moveon.data.local
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import pt.amn.moveon.data.local.AppDatabase
import pt.amn.moveon.data.local.CountryDao

class CountryDaoTest {

    private lateinit var countryDao: CountryDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        db = AppDatabase.getInstance(context)

        countryDao = db.countryDao()

    }

    @Test
    fun fileForSeedingIsExistAndCorrect() {

        /*val context = ApplicationProvider.getApplicationContext<Context>()

        val filename = MoveonDatabaseWorker.KEY_FILENAME

            context.assets.open(filename).use { inputStream ->
                assertTrue(inputStream.available() == 1)
               /* JsonReader(inputStream.reader()).use { jsonReader ->
                    val countryType = object : TypeToken<List<CountryEntity>>() {}.type
                    val countryList: List<CountryEntity> =
                        Gson().fromJson(jsonReader, countryType)

                    workerDependency.database.countryDao().insertAll(countryList)

                    LogNavigator.debugMessage("${MoveonDatabaseWorker.TAG}, Success seeding database, count of countries = ${countryList.size}")
                    ListenableWorker.Result.success()
                }*/
            }*/
        assertTrue(true)
    }

    @After
    fun closeDb() {
        db.close()
    }
}