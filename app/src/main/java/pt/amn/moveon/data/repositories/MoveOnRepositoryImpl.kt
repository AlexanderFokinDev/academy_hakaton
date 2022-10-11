package pt.amn.moveon.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import pt.amn.moveon.data.local.*
import pt.amn.moveon.data.models.toDomainModel
import pt.amn.moveon.data.models.toEntityModel
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.domain.models.UseCaseResult
import pt.amn.moveon.domain.repositories.MoveOnRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoveOnRepositoryImpl @Inject constructor(private val database: AppDatabase) :
    MoveOnRepository {

    override suspend fun getCountries(): LiveData<List<Country>> =
        Transformations.map(database.countryDao().getAllFlow().asLiveData()) { listEntities ->
            listEntities.map { entity ->
                entity.toDomainModel()
            }
        }

    override suspend fun getVisitedCountries(): LiveData<List<Country>> =
        Transformations.map(
            database.countryDao().getVisitedCountriesFlow().asLiveData()
        ) { listEntities ->
            listEntities.map { entity ->
                entity.toDomainModel()
            }
        }

    override suspend fun updateCountry(country: Country)
            : UseCaseResult<Boolean> {
        updateCountryInDatabase(country)
        return UseCaseResult(isError = false, data = true, description = "")
    }

    override suspend fun getVisitedPlaces(): LiveData<List<MoveOnPlace>> =
        Transformations.map(database.placeDao().getAllFlow().asLiveData()) { listEntities ->
            listEntities.map { entity ->
                entity.toDomainModel()
            }
        }

    override suspend fun getVisitedPlacesInCountry(countryId: Int): LiveData<List<MoveOnPlace>> =
        Transformations.map(
            database.placeDao().getVisitedPlacesInCountryFlow(countryId).asLiveData()
        ) { listEntities ->
            listEntities.map { entity ->
                entity.toDomainModel()
            }
        }

    override suspend fun getCountryById(id: Int): UseCaseResult<Country> =
        UseCaseResult(isError = false, fetchCountryFromDatabase(id), description = "")

    override suspend fun addPlace(place: MoveOnPlace)
            : UseCaseResult<Boolean> {
        addPlaceInDatabase(place)
        return UseCaseResult(isError = false, data = true, description = "")
    }

    override suspend fun deletePlace(place: MoveOnPlace): UseCaseResult<Boolean> {
        deletePlaceInDatabase(place)
        return UseCaseResult(isError = false, data = true, description = "")
    }

    override suspend fun getPlaceByName(name: String): UseCaseResult<List<MoveOnPlace>> {
        val result = fetchPlaceFromDatabaseByName(name)
        return if (result == null) {
            UseCaseResult(isError = false, listOf(), description = "")
        } else {
            UseCaseResult(isError = false, listOf(result), description = "")
        }
    }

    private suspend fun fetchCountryFromDatabase(id: Int): Country =
        database.countryDao().getCountryById(id).toDomainModel()

    private suspend fun updateCountryInDatabase(country: Country) =
        database.countryDao().update(country.toEntityModel())

    private suspend fun addPlaceInDatabase(place: MoveOnPlace) =
        database.placeDao().insert(place.toEntityModel())

    private suspend fun deletePlaceInDatabase(place: MoveOnPlace) =
        database.placeDao().delete(place.toEntityModel())

    private suspend fun fetchPlaceFromDatabaseByName(name: String): MoveOnPlace? =
        database.placeDao().getPlaceByName(name)?.toDomainModel()

}