package pt.amn.moveon.data.local

import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.serialization.Serializable
import pt.amn.moveon.domain.models.Continent
import pt.amn.moveon.domain.models.Country

data class CountryWithContinent(
    @Embedded val countryEntity: CountryEntity,
    @Relation(
        parentColumn = "continentId",
        entityColumn = "id"
    )
    val continentEntity: ContinentEntity
)

fun CountryWithContinent.toDomainModel(): Country {

    return Country(
        id = this.countryEntity.id,
        nameEn = this.countryEntity.nameEn,
        nameRu = this.countryEntity.nameRu,
        latitude = this.countryEntity.latitude,
        longitude = this.countryEntity.longitude,
        visited = this.countryEntity.visited,
        flagResId = this.countryEntity.flagResId,
        alpha2 = this.countryEntity.alpha2,
        continent = this.continentEntity.toDomainModel()// ?: Continent(0, "test", "test")
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
        continentId = this.continent.id
    )
}

fun CountryWithContinent.toJsonModel(): CountryJson {

    return CountryJson(
        id = this.countryEntity.id
    )
}