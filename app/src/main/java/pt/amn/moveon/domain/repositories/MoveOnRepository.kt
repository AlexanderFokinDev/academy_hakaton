package pt.amn.moveon.domain.repositories

import androidx.lifecycle.LiveData
import pt.amn.moveon.domain.models.*

interface MoveOnRepository {

    suspend fun getCountries(): LiveData<List<Country>>

    suspend fun getVisitedCountries(): LiveData<List<Country>>

    suspend fun updateCountry(country: Country): UseCaseResult<Boolean>

    suspend fun getVisitedPlaces(): LiveData<List<MoveOnPlace>>

    suspend fun getVisitedPlacesInCountry(countryId: Int): LiveData<List<MoveOnPlace>>

    suspend fun getCountryById(id: Int): UseCaseResult<Country>

    suspend fun addPlace(place: MoveOnPlace): UseCaseResult<Boolean>

    suspend fun deletePlace(place: MoveOnPlace): UseCaseResult<Boolean>

    suspend fun getPlaceByName(name: String): UseCaseResult<List<MoveOnPlace>>

}