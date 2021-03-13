package pt.amn.moveon.domain.repositories

import androidx.lifecycle.LiveData
import pt.amn.moveon.data.local.PlaceEntity
import pt.amn.moveon.data.repositories.RepositoryResult
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.Place

interface MoveOnRepository {

    suspend fun getCountries(): RepositoryResult<Country>

    suspend fun getVisitedCountries(): RepositoryResult<Country>

    suspend fun updateCountry(country: Country): RepositoryResult<Boolean>

    suspend fun getVisitedPlaces(): RepositoryResult<Place>

    suspend fun getVisitedPlacesInCountry(countryId: Int): RepositoryResult<Place>

    suspend fun getCountryById(id: Int): RepositoryResult<Country>

    suspend fun addPlace(place: Place): RepositoryResult<Boolean>

    suspend fun getPlaceByName(name: String): RepositoryResult<Place>

}