package pt.amn.moveon.domain.usecases

import androidx.lifecycle.LiveData
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.repositories.MoveOnRepository

class GetCountriesUseCase(private val repository: MoveOnRepository) {

    suspend fun execute(onlyVisited: Boolean): LiveData<List<Country>> =
        if (onlyVisited) {
            repository.getVisitedCountries()
        } else {
            repository.getCountries()
        }
}