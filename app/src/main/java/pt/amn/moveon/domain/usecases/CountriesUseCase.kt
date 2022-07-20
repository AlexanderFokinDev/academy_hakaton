package pt.amn.moveon.domain.usecases

import androidx.lifecycle.LiveData
import pt.amn.moveon.data.local.CountryEntity
import pt.amn.moveon.data.local.CountryWithContinent
import pt.amn.moveon.data.local.PlaceEntity
import pt.amn.moveon.data.repositories.RepositoryResult
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.domain.repositories.MoveOnRepository

class CountriesUseCase(private val repository: MoveOnRepository) {

    suspend fun getCountries() : LiveData<List<CountryWithContinent>> {
        return repository.getCountries()
    }

    suspend fun getVisitedCountries() : LiveData<List<CountryWithContinent>> {
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
}