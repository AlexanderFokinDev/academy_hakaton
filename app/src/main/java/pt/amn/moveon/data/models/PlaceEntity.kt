package pt.amn.moveon.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.amn.moveon.domain.models.MoveOnPlace

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "country_id") val country_id: Int
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