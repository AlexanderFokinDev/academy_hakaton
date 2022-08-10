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
    @ColumnInfo(name = "id")
    val id: Int,
    val nameEn: String,
    val nameRu: String,
    val latitude: Double,
    val longitude: Double,
    val visited: Boolean = false,
    val flagResId: String = "",
    val alpha2: String = "",
    val continentId: Int = 0
)