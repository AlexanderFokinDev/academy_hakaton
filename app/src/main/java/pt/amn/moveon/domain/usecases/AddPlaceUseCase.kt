package pt.amn.moveon.domain.usecases

import pt.amn.moveon.data.repositories.RepositoryResult
import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.domain.repositories.MoveOnRepository

class AddPlaceUseCase(private val repository: MoveOnRepository) {

    suspend fun execute(place: MoveOnPlace)
            : RepositoryResult<Boolean> {
        // check dublicates
        val result = repository.getPlaceByName(place.name)
        if (result.dataList.isNotEmpty()) {
            return RepositoryResult(false, listOf(), "")
        }
        return repository.addPlace(place)
    }
}