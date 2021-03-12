package pt.amn.moveon.domain.usecases

import pt.amn.moveon.data.repositories.RepositoryResult
import pt.amn.moveon.domain.models.Country
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
}