package pt.amn.moveon.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.amn.moveon.domain.models.Country

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    val nameEn: String,
    val nameRu: String,
    val latitude: Double,
    val longitude: Double,
    val visited: Boolean = false,
    val flagResId: String = "",
    val alpha2: String = "",
    val continent: String
)

fun CountryEntity.toDomainModel(): Country {

    return Country(
        id = this.id,
        nameEn = this.nameEn,
        nameRu = this.nameRu,
        latitude = this.latitude,
        longitude = this.longitude,
        visited = this.visited,
        flagResId = this.flagResId,
        alpha2 = this.alpha2,
        continent = this.continent
    )
}

fun Country.toEntityModel(): CountryEntity {

    return CountryEntity(
        id = this.id,
        nameEn = this.nameEn,
        nameRu = this.nameRu,
        latitude = this.latitude,
        longitude = this.longitude,
        visited = this.visited,
        flagResId = this.flagResId,
        alpha2 = this.alpha2,
        continent = this.continent
    )
}

fun CountryEntity.toJsonModel(): CountryJson {

    return CountryJson(
        id = this.id
    )
}