package pt.amn.moveon.domain.usecases

import androidx.lifecycle.LiveData
import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.domain.repositories.MoveOnRepository

class GetPlacesUseCase(private val repository: MoveOnRepository) {

    suspend fun execute(countryId: Int = -1) : LiveData<List<MoveOnPlace>> =
        if (countryId == -1) {
            repository.getVisitedPlaces()
        } else {
            repository.getVisitedPlacesInCountry(countryId)
        }
}