package pt.amn.moveon.data.local
import android.content.Context
import com.google.gson.stream.JsonReader
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import pt.amn.moveon.common.COUNTRIES_DATA_FILENAME
import pt.amn.moveon.common.LogNavigator
import pt.amn.moveon.data.local.AppDatabase
import pt.amn.moveon.data.local.CountryDao
import pt.amn.moveon.workers.MoveonDatabaseWorker

@RunWith(AndroidJUnit4::class)
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

    @After
    fun closeDb() {
        db.close()
    }
}