package pt.amn.moveon.domain.models

import java.util.*

data class Country(
    val id: Int,
    val nameEn: String,
    val nameRu: String,
    val latitude: Double,
    val longitude: Double,
    var visited: Boolean,
    val flagResId: String,
    val alpha2: String
) {

    fun getLocalName(): String =
        when (Locale.getDefault().language) {
            "ru" -> nameRu
            else -> nameEn
        }

}