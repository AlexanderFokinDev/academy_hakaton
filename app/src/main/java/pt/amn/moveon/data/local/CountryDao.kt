package pt.amn.moveon.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Query("SELECT * FROM countries ORDER BY nameRu")
    fun getAll(): Flow<List<CountryEntity>>

    @Query("SELECT * FROM countries WHERE visited")
    fun getVisitedCountries(): Flow<List<CountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<CountryEntity>)

    @Query("SELECT * FROM countries WHERE id = :id")
    suspend fun getCountryById(id: Int): CountryEntity

    @Update
    suspend fun update(country: CountryEntity)
}