package pt.amn.moveon.data.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import pt.amn.moveon.domain.models.Continent
import pt.amn.moveon.domain.models.Country

@Serializable
@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "nameEn") val nameEn: String,
    @ColumnInfo(name = "nameEn") val nameRu: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "visited") val visited: Boolean = false,
    @ColumnInfo(name = "flagResId") val flagResId: String = "",
    @ColumnInfo(name = "alpha2") val alpha2: String = "",
    @ColumnInfo(name = "continentId") val continentId: Int = 0
)