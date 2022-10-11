package pt.amn.moveon.data.models

import androidx.room.Embedded
import androidx.room.Relation
import pt.amn.moveon.domain.models.Country

data class CountryWithContinentEntity(
    @Embedded val countryEntity: CountryEntity,
    @Relation(
        parentColumn = "continentId",
        entityColumn = "id"
    )
    val continentEntity: ContinentEntity
)

fun CountryWithContinentEntity.toDomainModel(): Country {

    return Country(
        id = this.countryEntity.id,
        nameEn = this.countryEntity.nameEn,
        nameRu = this.countryEntity.nameRu,
        latitude = this.countryEntity.latitude,
        longitude = this.countryEntity.longitude,
        visited = this.countryEntity.visited,
        flagResId = this.countryEntity.flagResId,
        alpha2 = this.countryEntity.alpha2,
        continent = this.continentEntity.toDomainModel()
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

fun CountryWithContinentEntity.toJsonModel(): CountryJson {

    return CountryJson(
        id = this.countryEntity.id
    )
}