package pt.amn.moveon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import pt.amn.moveon.common.COUNTRIES_DATA_FILENAME
import pt.amn.moveon.common.DATABASE_NAME
import pt.amn.moveon.common.LogNavigator
import pt.amn.moveon.workers.MoveonDatabaseWorker
import pt.amn.moveon.workers.MoveonDatabaseWorker.Companion.KEY_FILENAME

@Database(entities = [CountryEntity::class, PlaceEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao
    abstract fun placeDao(): PlaceDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { db -> instance = db}
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context) : AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<MoveonDatabaseWorker>()
                                .setInputData(workDataOf(KEY_FILENAME to COUNTRIES_DATA_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
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

    }
}
