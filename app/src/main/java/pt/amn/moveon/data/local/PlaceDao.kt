package pt.amn.moveon.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaceDao {

    @Query("SELECT * FROM places ORDER BY name")
    suspend fun getAll(): List<PlaceEntity>

    @Query("SELECT * FROM places WHERE country_id = :countryId ORDER BY name")
    suspend fun getVisitedPlacesInCountry(countryId: Int): List<PlaceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: PlaceEntity)

    @Query("SELECT * FROM places WHERE name = :name")
    suspend fun getPlaceByName(name: String): PlaceEntity?

}