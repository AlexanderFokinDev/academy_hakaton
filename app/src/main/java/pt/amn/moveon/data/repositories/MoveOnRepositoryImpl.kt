package pt.amn.moveon.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.flow.map
import pt.amn.moveon.data.local.*
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.domain.repositories.MoveOnRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoveOnRepositoryImpl @Inject constructor(private val database: AppDatabase) :
    MoveOnRepository {

    override suspend fun getCountries(): LiveData<List<CountryEntity>> =
        database.countryDao().getAll().asLiveData()

    override suspend fun getVisitedCountries(): LiveData<List<CountryEntity>> =
        database.countryDao().getVisitedCountries().asLiveData()

    override suspend fun updateCountry(country: Country)
            : RepositoryResult<Boolean> {
        updateCountryInDatabase(country)
        return RepositoryResult(false, listOf(), "")
    }

    override suspend fun getVisitedPlaces(): LiveData<List<PlaceEntity>> =
        database.placeDao().getAll().asLiveData()

    override suspend fun getVisitedPlacesInCountry(countryId: Int): LiveData<List<PlaceEntity>> =
        database.placeDao().getVisitedPlacesInCountry(countryId).asLiveData()

    override suspend fun getCountryById(id: Int): RepositoryResult<Country> =
        RepositoryResult(false, listOf(fetchCountryFromDatabase(id)), "")

    override suspend fun addPlace(place: MoveOnPlace)
            : RepositoryResult<Boolean> {
        addPlaceInDatabase(place)
        return RepositoryResult(false, listOf(), "")
    }

    override suspend fun getPlaceByName(name: String): RepositoryResult<MoveOnPlace> {
        val result = fetchPlaceFromDatabaseByName(name)
        return if (result == null) {
            RepositoryResult(false, listOf(), "")
        } else {
            RepositoryResult(false, listOf(result), "")
        }
    }

    private suspend fun fetchCountryFromDatabase(id: Int): Country =
        database.countryDao().getCountryById(id).toDomainModel()

    private suspend fun updateCountryInDatabase(country: Country) =
        database.countryDao().update(country.toEntityModel())

    private suspend fun addPlaceInDatabase(place: MoveOnPlace) =
        database.placeDao().insert(place.toEntityModel())

    private suspend fun fetchPlaceFromDatabaseByName(name: String): MoveOnPlace? =
        database.placeDao().getPlaceByName(name)?.toDomainModel()

}

class RepositoryResult<T>(
    var isError: Boolean,
    var dataList: List<T>,
    var description: String = "")