package pt.amn.moveon.domain.usecases

import com.google.android.libraries.places.api.model.Place
import pt.amn.moveon.data.repositories.RepositoryResult
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.domain.repositories.MoveOnRepository

class GetCountriesUseCase(private val repository: MoveOnRepository) {

    suspend fun getCountries() : RepositoryResult<Country> {
        return repository.getCountries()
    }

    suspend fun getVisitedCountries() : RepositoryResult<Country> {
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

    suspend fun getVisitedPlaces() : RepositoryResult<MoveOnPlace> {
        return repository.getVisitedPlaces()
    }

    suspend fun getVisitedPlacesInCountry(countryId: Int) : RepositoryResult<MoveOnPlace> {
        return repository.getVisitedPlacesInCountry(countryId)
    }

    suspend fun addPlace(place: MoveOnPlace)
            : RepositoryResult<Boolean> {
        // check dublicates
        val result = repository.getPlaceByName(place.name)
        if (result.dataList.size > 0) {
            return RepositoryResult(false, listOf(), "")
        }
        return repository.addPlace(place)
    }
}