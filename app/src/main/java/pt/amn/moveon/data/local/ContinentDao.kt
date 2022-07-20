package pt.amn.moveon.data.local

import androidx.room.*

@Dao
interface ContinentDao {

    //TODO
    /*@Query("SELECT * FROM countries ORDER BY nameRu")
    fun getAllFlow(): Flow<List<CountryEntity>>

    @Query("SELECT * FROM countries WHERE visited")
    fun getVisitedCountriesFlow(): Flow<List<CountryEntity>>

    @Query("SELECT * FROM countries WHERE visited")
    fun getVisitedCountries(): List<CountryEntity>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(continents: List<ContinentEntity>)

    @Query("SELECT * FROM continents WHERE id = :id")
    suspend fun getContinentById(id: Int): ContinentEntity

    /*@Update
    suspend fun update(country: CountryEntity)

    @Query("UPDATE countries SET visited = 0")
    suspend fun removeVisitedFlagForAll()

    @Query("UPDATE countries SET visited = 1 WHERE id = :id")
    suspend fun setVisitedFlag(id: Int)*/
}