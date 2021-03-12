package pt.amn.moveon.data.local

import androidx.room.*

@Dao
interface CountryDao {

    @Query("SELECT * FROM countries ORDER BY nameRu")
    suspend fun getAll(): List<CountryEntity>

    @Query("SELECT * FROM countries WHERE visited")
    suspend fun getVisitedCountries(): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<CountryEntity>)

    @Update
    suspend fun update(country: CountryEntity)
}