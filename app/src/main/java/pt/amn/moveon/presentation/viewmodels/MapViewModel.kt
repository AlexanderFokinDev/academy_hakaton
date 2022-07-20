package pt.amn.moveon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.amn.moveon.data.local.CountryEntity
import pt.amn.moveon.data.local.CountryWithContinent
import pt.amn.moveon.data.local.PlaceEntity
import pt.amn.moveon.domain.repositories.MoveOnRepository
import pt.amn.moveon.domain.usecases.CountriesUseCase
import pt.amn.moveon.domain.usecases.PlacesUseCase
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MoveOnRepository
) : ViewModel() {

    private val interactorCountries = CountriesUseCase(repository)
    private val interactorPlaces = PlacesUseCase(repository)

    private lateinit var _mVisitedCountries: LiveData<List<CountryWithContinent>>
    val visitedCountries : LiveData<List<CountryWithContinent>> get() = _mVisitedCountries

    private lateinit var _mVisitedPlaces: LiveData<List<PlaceEntity>>
    val visitedPlaces : LiveData<List<PlaceEntity>> get() = _mVisitedPlaces

    init {
        viewModelScope.launch {
            _mVisitedCountries = interactorCountries.getVisitedCountries()
            _mVisitedPlaces = interactorPlaces.getVisitedPlaces()
        }
    }

}