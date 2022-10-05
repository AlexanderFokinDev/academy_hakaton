package pt.amn.moveon.domain.repositories

import androidx.lifecycle.LiveData
import pt.amn.moveon.data.local.CountryWithContinent
import pt.amn.moveon.data.local.PlaceEntity
import pt.amn.moveon.data.repositories.RepositoryResult
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.MoveOnPlace

interface MoveOnRepository {

    suspend fun getCountries(): LiveData<List<CountryWithContinent>>

    suspend fun getVisitedCountries(): LiveData<List<CountryWithContinent>>

    suspend fun updateCountry(country: Country): RepositoryResult<Boolean>

    suspend fun getVisitedPlaces(): LiveData<List<PlaceEntity>>

    suspend fun getVisitedPlacesInCountry(countryId: Int): LiveData<List<PlaceEntity>>

    suspend fun getCountryById(id: Int): RepositoryResult<Country>

    suspend fun addPlace(place: MoveOnPlace): RepositoryResult<Boolean>

    suspend fun deletePlace(place: MoveOnPlace): RepositoryResult<Boolean>

    suspend fun getPlaceByName(name: String): RepositoryResult<MoveOnPlace>

}