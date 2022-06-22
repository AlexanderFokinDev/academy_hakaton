package pt.amn.moveon.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Query("SELECT * FROM countries ORDER BY nameRu")
    fun getAllFlow(): Flow<List<CountryEntity>>

    @Query("SELECT * FROM countries WHERE visited")
    fun getVisitedCountriesFlow(): Flow<List<CountryEntity>>

    @Query("SELECT * FROM countries WHERE visited")
    fun getVisitedCountries(): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<CountryEntity>)

    @Query("SELECT * FROM countries WHERE id = :id")
    suspend fun getCountryById(id: Int): CountryEntity

    @Update
    suspend fun update(country: CountryEntity)

    @Query("UPDATE countries SET visited = 0")
    suspend fun removeVisitedFlagForAll()

    @Query("UPDATE countries SET visited = 1 WHERE id = :id")
    suspend fun setVisitedFlag(id: Int)
}