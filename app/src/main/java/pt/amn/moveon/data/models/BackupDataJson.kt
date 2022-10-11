// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json             = Json(JsonConfiguration.Stable)
// val standardResponse = json.parse(StandardResponse.serializer(), jsonString)

package pt.amn.moveon.data.models

import kotlinx.serialization.*

@Serializable
data class BackupDataJson (
    val visitedPlaces: List<PlaceJson>,
    val visitedCountries: List<CountryJson>
)

@Serializable
data class CountryJson (
    val id: Int
)

@Serializable
data class PlaceJson (
    val countryID: Int,
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val name: String
)