package pt.amn.moveon.domain.usecases

import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.domain.models.UseCaseResult
import pt.amn.moveon.domain.repositories.MoveOnRepository

class AddPlaceUseCase(private val repository: MoveOnRepository) {

    suspend fun execute(place: MoveOnPlace)
            : UseCaseResult<Boolean> {

        // check dublicates
        val result = repository.getPlaceByName(place.name)

        if (result.data.isNotEmpty()) {
            return UseCaseResult(
                isError = false,
                data = false,
                description = "Place was added before"
            )
        }

        return repository.addPlace(place)
    }
}