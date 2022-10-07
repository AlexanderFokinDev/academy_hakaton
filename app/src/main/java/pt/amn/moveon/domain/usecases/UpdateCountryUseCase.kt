package pt.amn.moveon.domain.usecases

import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.UseCaseResult
import pt.amn.moveon.domain.repositories.MoveOnRepository

class UpdateCountryUseCase(private val repository: MoveOnRepository) {

    suspend fun execute(country: Country)
            : UseCaseResult<Boolean> {
        return repository.updateCountry(country)
    }
}