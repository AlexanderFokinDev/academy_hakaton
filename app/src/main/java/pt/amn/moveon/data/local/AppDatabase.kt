package pt.amn.moveon.data.local

import android.content.Context
import android.provider.Contacts.SettingsColumns.KEY
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import pt.amn.moveon.common.CONTINENTS_DATA_FILENAME
import pt.amn.moveon.common.COUNTRIES_DATA_FILENAME
import pt.amn.moveon.common.DATABASE_NAME
import pt.amn.moveon.workers.MoveonDatabaseWorker
import pt.amn.moveon.workers.MoveonDatabaseWorker.Companion.KEY_FILENAME_CONTINENTS_ASSET
import pt.amn.moveon.workers.MoveonDatabaseWorker.Companion.KEY_FILENAME_COUNTRIES_ASSET

@Database(
    entities = [CountryEntity::class, PlaceEntity::class, ContinentEntity::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao
    abstract fun continentDao(): ContinentDao
    abstract fun placeDao(): PlaceDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { db -> instance = db }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<MoveonDatabaseWorker>()
                                .setInputData(
                                    workDataOf(
                                        KEY_FILENAME_COUNTRIES_ASSET to COUNTRIES_DATA_FILENAME,
                                        KEY_FILENAME_CONTINENTS_ASSET to CONTINENTS_DATA_FILENAME
                                    )
                                )
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            /*val request = OneTimeWorkRequestBuilder<MoveonDatabaseWorker>()
                                .setInputData(
                                    workDataOf(
                                        KEY_FILENAME_COUNTRIES_ASSET to COUNTRIES_DATA_FILENAME,
                                        KEY_FILENAME_CONTINENTS_ASSET to CONTINENTS_DATA_FILENAME
                                    )
                                )
                                .build()
                            WorkManager.getInstance(context).enqueue(request)*/
                        }
                    }
                )
                .build()
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE countries ADD COLUMN continent TEXT DEFAULT '' NOT NULL")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {

                // Add new table continents
                database.execSQL(
                    "CREATE TABLE continents (id INTEGER NOT NULL, nameEn TEXT NOT NULL, " +
                            "nameRu TEXT NOT NULL, PRIMARY KEY(id))"
                )

                // We can't delete column in SQL simple way
                database.execSQL("CREATE TABLE countries_backup (id INTEGER NOT NULL, nameEn TEXT NOT NULL" +
                    ",nameRu TEXT NOT NULL, latitude REAL NOT NULL, longitude REAL NOT NULL, " +
                    "visited Integer DEFAULT 0 NOT NULL, flagResId TEXT DEFAULT '' NOT NULL, " +
                    "alpha2 TEXT DEFAULT '' NOT NULL, PRIMARY KEY(id))"
                )

                database.execSQL("INSERT INTO countries_backup SELECT id, nameEn, nameRu, " +
                        "latitude, longitude, visited, flagResId, alpha2 FROM countries")

                database.execSQL("DROP TABLE countries")

                // Add column continentId
                database.execSQL("ALTER TABLE countries_backup ADD COLUMN continentId INTEGER DEFAULT 0 NOT NULL")

                database.execSQL("ALTER TABLE countries_backup RENAME TO countries")

            }
        }

    }
}
