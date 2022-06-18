package pt.amn.moveon.domain.usecases

import androidx.lifecycle.LiveData
import pt.amn.moveon.data.local.CountryEntity
import pt.amn.moveon.data.local.PlaceEntity
import pt.amn.moveon.data.repositories.RepositoryResult
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.domain.repositories.MoveOnRepository

class GetCountriesUseCase(private val repository: MoveOnRepository) {

    suspend fun getCountries() : LiveData<List<CountryEntity>> {
        return repository.getCountries()
    }

    suspend fun getVisitedCountries() : LiveData<List<CountryEntity>> {
        return repository.getVisitedCountries()
    }

    suspend fun changeVisitedFlagOfCountry(country: Country, visited: Boolean)
            : RepositoryResult<Boolean> {
        country.visited = visited
        return repository.updateCountry(country)
    }

    suspend fun getCountryById(id: Int) : RepositoryResult<Country> {
        return repository.getCountryById(id)
    }

    suspend fun getVisitedPlaces() : LiveData<List<PlaceEntity>> {
        return repository.getVisitedPlaces()
    }

    suspend fun getVisitedPlacesInCountry(countryId: Int) : LiveData<List<PlaceEntity>> {
        return repository.getVisitedPlacesInCountry(countryId)
    }

    suspend fun addPlace(place: MoveOnPlace)
            : RepositoryResult<Boolean> {
        // check dublicates
        val result = repository.getPlaceByName(place.name)
        if (result.dataList.isNotEmpty()) {
            return RepositoryResult(false, listOf(), "")
        }
        return repository.addPlace(place)
    }
}