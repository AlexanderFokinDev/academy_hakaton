package pt.amn.moveon.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Country(
    val id: Int,
    val nameEn: String,
    val nameRu: String,
    val latitude: Double,
    val longitude: Double,
    var visited: Boolean,
    val flagResId: String,
    val alpha2: String,
    val continent: Continent
) : Parcelable, PartOfTheWorld {

    fun getLocalName(): String =
        when (Locale.getDefault().language) {
            "ru" -> nameRu
            else -> nameEn
        }

}