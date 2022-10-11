package pt.amn.moveon.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "nameEn") val nameEn: String,
    @ColumnInfo(name = "nameRu") val nameRu: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "visited") val visited: Boolean = false,
    @ColumnInfo(name = "flagResId") val flagResId: String = "",
    @ColumnInfo(name = "alpha2") val alpha2: String = "",
    @ColumnInfo(name = "continentId") val continentId: Int = 0
)