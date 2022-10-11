package pt.amn.moveon.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pt.amn.moveon.data.models.PlaceEntity

@Dao
interface PlaceDao {

    @Query("SELECT * FROM places ORDER BY name")
    fun getAllFlow(): Flow<List<PlaceEntity>>

    @Query("SELECT * FROM places ORDER BY name")
    fun getAll(): List<PlaceEntity>

    @Query("SELECT * FROM places WHERE country_id = :countryId ORDER BY name")
    fun getVisitedPlacesInCountryFlow(countryId: Int): Flow<List<PlaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: PlaceEntity)

    @Delete
    suspend fun delete(place: PlaceEntity)

    @Query("DELETE FROM places")
    suspend fun deleteAll()

    @Query("SELECT * FROM places WHERE name = :name")
    suspend fun getPlaceByName(name: String): PlaceEntity?

}