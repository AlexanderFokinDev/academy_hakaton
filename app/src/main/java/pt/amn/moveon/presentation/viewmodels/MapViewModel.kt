package pt.amn.moveon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.amn.moveon.data.local.CountryEntity
import pt.amn.moveon.data.local.PlaceEntity
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.domain.repositories.MoveOnRepository
import pt.amn.moveon.domain.usecases.GetCountriesUseCase
import pt.amn.moveon.presentation.viewmodels.utils.Resource
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MoveOnRepository
) : ViewModel() {

    private val interactor = GetCountriesUseCase(repository)

    private lateinit var _mVisitedCountries: LiveData<List<CountryEntity>>
    val visitedCountries : LiveData<List<CountryEntity>> get() = _mVisitedCountries

    private lateinit var _mVisitedPlaces: LiveData<List<PlaceEntity>>
    val visitedPlaces : LiveData<List<PlaceEntity>> get() = _mVisitedPlaces

    init {
        viewModelScope.launch {
            _mVisitedCountries = interactor.getVisitedCountries()
            _mVisitedPlaces = interactor.getVisitedPlaces()
        }
    }

}