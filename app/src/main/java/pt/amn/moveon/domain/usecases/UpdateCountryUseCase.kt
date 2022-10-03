package pt.amn.moveon.domain.usecases

import pt.amn.moveon.data.repositories.RepositoryResult
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.repositories.MoveOnRepository

class UpdateCountryUseCase(private val repository: MoveOnRepository) {

    suspend fun execute(country: Country)
            : RepositoryResult<Boolean> {
        return repository.updateCountry(country)
    }
}