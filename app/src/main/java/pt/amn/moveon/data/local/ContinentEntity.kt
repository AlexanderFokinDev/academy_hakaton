package pt.amn.moveon.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import pt.amn.moveon.domain.models.Continent

@Serializable
@Entity(tableName = "continents")
data class ContinentEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    val nameEn: String,
    val nameRu: String,
)

fun ContinentEntity.toDomainModel(): Continent {

    return Continent(
        id = this.id,
        nameEn = this.nameEn,
        nameRu = this.nameRu
    )
}

fun Continent.toEntityModel(): ContinentEntity {

    return ContinentEntity(
        id = this.id,
        nameEn = this.nameEn,
        nameRu = this.nameRu
    )
}