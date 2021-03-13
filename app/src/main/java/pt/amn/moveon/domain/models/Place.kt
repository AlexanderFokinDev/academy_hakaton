package pt.amn.moveon.domain.models

data class Place(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country_id: Int
)