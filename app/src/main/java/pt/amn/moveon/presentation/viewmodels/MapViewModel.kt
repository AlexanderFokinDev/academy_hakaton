package pt.amn.moveon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.MoveOnPlace
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

    private lateinit var _mVisitedCountries: LiveData<List<Country>>
    val visitedCountries : LiveData<List<Country>> get() = _mVisitedCountries

    private lateinit var _mVisitedPlaces: LiveData<List<MoveOnPlace>>
    val visitedPlaces : LiveData<List<MoveOnPlace>> get() = _mVisitedPlaces

    init {
        viewModelScope.launch {
            _mVisitedCountries = interactorCountries.execute(onlyVisited = true)
            _mVisitedPlaces = interactorGetPlaces.execute()
        }
    }

}