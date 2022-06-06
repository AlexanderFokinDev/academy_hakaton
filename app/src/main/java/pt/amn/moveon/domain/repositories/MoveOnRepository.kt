package pt.amn.moveon.domain.repositories

import pt.amn.moveon.data.repositories.RepositoryResult
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.MoveOnPlace

interface MoveOnRepository {

    suspend fun getCountries(): RepositoryResult<Country>

    suspend fun getVisitedCountries(): RepositoryResult<Country>

    suspend fun updateCountry(country: Country): RepositoryResult<Boolean>

    suspend fun getVisitedPlaces(): RepositoryResult<MoveOnPlace>

    suspend fun getVisitedPlacesInCountry(countryId: Int): RepositoryResult<MoveOnPlace>

    suspend fun getCountryById(id: Int): RepositoryResult<Country>

    suspend fun addPlace(place: MoveOnPlace): RepositoryResult<Boolean>

    suspend fun getPlaceByName(name: String): RepositoryResult<MoveOnPlace>

}