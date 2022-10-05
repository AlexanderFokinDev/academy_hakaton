package pt.amn.moveon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.amn.moveon.data.local.CountryWithContinent
import pt.amn.moveon.data.local.PlaceEntity
import pt.amn.moveon.domain.repositories.MoveOnRepository
import pt.amn.moveon.domain.usecases.GetCountriesUseCase
import pt.amn.moveon.domain.usecases.GetPlacesUseCase
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MoveOnRepository
) : ViewModel() {

    private val interactorCountries = GetCountriesUseCase(repository)
    private val interactorGetPlaces = GetPlacesUseCase(repository)

    private lateinit var _mVisitedCountries: LiveData<List<CountryWithContinent>>
    val visitedCountries : LiveData<List<CountryWithContinent>> get() = _mVisitedCountries

    private lateinit var _mVisitedPlaces: LiveData<List<PlaceEntity>>
    val visitedPlaces : LiveData<List<PlaceEntity>> get() = _mVisitedPlaces

    init {
        viewModelScope.launch {
            _mVisitedCountries = interactorCountries.execute(onlyVisited = true)
            _mVisitedPlaces = interactorGetPlaces.execute()
        }
    }

}