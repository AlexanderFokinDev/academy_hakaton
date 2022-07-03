package pt.amn.moveon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.amn.moveon.data.local.CountryEntity
import pt.amn.moveon.data.local.PlaceEntity
import pt.amn.moveon.domain.repositories.MoveOnRepository
import pt.amn.moveon.domain.usecases.*
import javax.inject.Inject

@HiltViewModel
class WorkplaceViewModel @Inject constructor(
    private val repository: MoveOnRepository
) : ViewModel() {

    private val interactorCountries = CountriesUseCase(repository)
    private val interactorPlaces = PlacesUseCase(repository)

    private lateinit var _mVisitedCountries: LiveData<List<CountryEntity>>
    val visitedCountries: LiveData<List<CountryEntity>> get() = _mVisitedCountries

    private lateinit var _mVisitedPlaces: LiveData<List<PlaceEntity>>
    val visitedPlaces: LiveData<List<PlaceEntity>> get() = _mVisitedPlaces

    var countVisitedCountries: Int = 0
    var countVisitedPlaces: Int = 0

    private val statisticsSolver = StatisticsSolver.Base()
    private val percentWorld get() = statisticsSolver.getPercentOfTheWorld(countVisitedCountries)
    private val level get() = statisticsSolver.getLevelOfTraveler(countVisitedCountries)

    private val _mFullStatistics = UpdateStatistics.Base()
    val fullStatistics get() = _mFullStatistics.getStatisticsOfTraveler(
            level,
            percentWorld,
            countVisitedCountries,
            countVisitedPlaces
        )

    init {
        viewModelScope.launch {
            _mVisitedCountries = interactorCountries.getVisitedCountries()
            _mVisitedPlaces = interactorPlaces.getVisitedPlaces()
        }
    }

}