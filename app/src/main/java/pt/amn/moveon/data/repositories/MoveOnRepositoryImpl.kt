package pt.amn.moveon.data.repositories

import pt.amn.moveon.data.local.AppDatabase
import pt.amn.moveon.data.local.toDomainModel
import pt.amn.moveon.data.local.toEntityModel
import pt.amn.moveon.domain.models.Country
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

    private suspend fun updateCountryInDatabase(country: Country) =
        database.countryDao().update(country.toEntityModel())

}

class RepositoryResult<T>(
    var isError: Boolean,
    var dataList: List<T>,
    var description: String = "") {
}