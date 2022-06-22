package pt.amn.moveon.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.amn.moveon.domain.models.MoveOnPlace

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country_id: Int
)

fun PlaceEntity.toDomainModel(): MoveOnPlace {

    return MoveOnPlace(
        id = this.id,
        name = this.name,
        latitude = this.latitude,
        longitude = this.longitude,
        country_id = this.country_id
    )
}

fun MoveOnPlace.toEntityModel(): PlaceEntity {

    return PlaceEntity(
        id = this.id,
        name = this.name,
        latitude = this.latitude,
        longitude = this.longitude,
        country_id = this.country_id
    )
}

fun PlaceEntity.toJsonModel(): PlaceJson {

    return PlaceJson(
        id = this.id,
        name = this.name,
        latitude = this.latitude,
        longitude = this.longitude,
        countryID = this.country_id
    )
}

fun PlaceJson.toEntityModel(): PlaceEntity {

    return PlaceEntity(
        id = this.id,
        name = this.name,
        latitude = this.latitude,
        longitude = this.longitude,
        country_id = this.countryID
    )
}