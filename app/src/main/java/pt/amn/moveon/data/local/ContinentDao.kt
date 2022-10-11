package pt.amn.moveon.data.local

import androidx.room.*
import pt.amn.moveon.data.models.ContinentEntity

@Dao
interface ContinentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(continents: List<ContinentEntity>)

    @Query("SELECT * FROM continents WHERE id = :id")
    suspend fun getContinentById(id: Int): ContinentEntity
}