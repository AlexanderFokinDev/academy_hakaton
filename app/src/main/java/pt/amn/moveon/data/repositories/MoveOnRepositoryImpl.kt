package pt.amn.moveon.data.repositories

import androidx.lifecycle.LiveData
import pt.amn.moveon.data.local.AppDatabase
import pt.amn.moveon.data.local.PlaceEntity
import pt.amn.moveon.data.local.toDomainModel
import pt.amn.moveon.data.local.toEntityModel
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.Place
import pt.amn.moveon.domain.repositories.MoveOnRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoveOnRepositoryImpl @Inject constructor(private val database: AppDatabase) :
    MoveOnRepository {

    override suspend fun getCountries(): RepositoryResult<Country> =
        RepositoryResult(false, fetchCountriesFromDatabase(), "")

    override suspend fun getVisitedCountries(): RepositoryResult<Country> =
        RepositoryResult(false, fetchVisitedCountriesFromDatabase(), "")

    override suspend fun updateCountry(country: Country)
            : RepositoryResult<Boolean> {
        updateCountryInDatabase(country)
        return RepositoryResult(false, listOf(), "")
    }

    override suspend fun getVisitedPlaces(): RepositoryResult<Place> =
        RepositoryResult(false, fetchVisitedPlacesFromDatabase(), "")

    override suspend fun getVisitedPlacesInCountry(countryId: Int): RepositoryResult<Place> =
        RepositoryResult(false, fetchVisitedPlacesInCountryFromDatabase(countryId), "")

    override suspend fun getCountryById(id: Int): RepositoryResult<Country> =
        RepositoryResult(false, listOf(fetchCountryFromDatabase(id)), "")

    override suspend fun addPlace(place: Place)
            : RepositoryResult<Boolean> {
        addPlaceInDatabase(place)
        return RepositoryResult(false, listOf(), "")
    }

    override suspend fun getPlaceByName(name: String): RepositoryResult<Place> {
        val result = fetchPlaceFromDatabaseByName(name)
        if (result == null) {
            return RepositoryResult(false, listOf(), "")
        } else {
            return RepositoryResult(false, listOf(result), "")
        }
    }


    private suspend fun fetchCountriesFromDatabase(): List<Country> =
        database.countryDao().getAll()
            .map { countryEntity ->
                countryEntity.toDomainModel()
            }

    private suspend fun fetchVisitedCountriesFromDatabase(): List<Country> =
        database.countryDao().getVisitedCountries()
            .map { countryEntity ->
                countryEntity.toDomainModel()
            }

    private suspend fun fetchCountryFromDatabase(id: Int): Country =
        database.countryDao().getCountryById(id).toDomainModel()

    private suspend fun updateCountryInDatabase(country: Country) =
        database.countryDao().update(country.toEntityModel())

    private suspend fun fetchVisitedPlacesFromDatabase(): List<Place> =
        database.placeDao().getAll()
            .map { placeEntity ->
                placeEntity.toDomainModel()
            }

    private suspend fun fetchVisitedPlacesInCountryFromDatabase(countryId: Int)
    : List<Place> {
        return database.placeDao().getVisitedPlacesInCountry(countryId)
            .map { placeEntity ->
                placeEntity.toDomainModel()
            }
    }


    private suspend fun addPlaceInDatabase(place: Place) =
        database.placeDao().insert(place.toEntityModel())

    private suspend fun fetchPlaceFromDatabaseByName(name: String): Place? =
        database.placeDao().getPlaceByName(name)?.toDomainModel()

}

class RepositoryResult<T>(
    var isError: Boolean,
    var dataList: List<T>,
    var description: String = "") {
}