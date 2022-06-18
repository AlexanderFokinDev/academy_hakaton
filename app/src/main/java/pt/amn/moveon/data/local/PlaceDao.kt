package pt.amn.moveon.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Query("SELECT * FROM places ORDER BY name")
    fun getAll(): Flow<List<PlaceEntity>>

    @Query("SELECT * FROM places WHERE country_id = :countryId ORDER BY name")
    fun getVisitedPlacesInCountry(countryId: Int): Flow<List<PlaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: PlaceEntity)

    @Query("SELECT * FROM places WHERE name = :name")
    suspend fun getPlaceByName(name: String): PlaceEntity?

}