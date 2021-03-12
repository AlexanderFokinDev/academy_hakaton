package pt.amn.moveon.domain.repositories

import pt.amn.moveon.data.repositories.RepositoryResult
import pt.amn.moveon.domain.models.Country

interface MoveOnRepository {

    suspend fun getCountries(): RepositoryResult<Country>

    suspend fun getVisitedCountries(): RepositoryResult<Country>

    suspend fun updateCountry(country: Country): RepositoryResult<Boolean>

}