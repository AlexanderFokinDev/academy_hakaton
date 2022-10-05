package pt.amn.moveon.domain.usecases

import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.domain.repositories.MoveOnRepository

class RemovePlaceUseCase(private val repository: MoveOnRepository) {

    suspend fun execute(place: MoveOnPlace) {
        repository.deletePlace(place)
    }

}