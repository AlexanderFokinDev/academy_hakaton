package pt.amn.moveon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.amn.moveon.data.local.PlaceEntity
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.domain.repositories.MoveOnRepository
import pt.amn.moveon.domain.usecases.GetCountriesUseCase
import pt.amn.moveon.presentation.viewmodels.utils.Resource
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: MoveOnRepository
) : ViewModel() {

    private val interactor = GetCountriesUseCase(repository)

    private lateinit var _mPlaces: LiveData<List<PlaceEntity>>
    val placesList : LiveData<List<PlaceEntity>> get() = _mPlaces

    private lateinit var country: Country

    fun setCountry(countrySelect: Country) {
        country = countrySelect
        viewModelScope.launch {
            _mPlaces = interactor.getVisitedPlacesInCountry(country.id)
        }
    }

    fun changeVisitedFlagOfCountry(country: Country, visited: Boolean) {
        viewModelScope.launch {
            interactor.changeVisitedFlagOfCountry(country, visited)
        }
    }

    fun addPlace(id: String, latitude: Double, longitude: Double, name: String, countryId: Int) {
        viewModelScope.launch {
            interactor.addPlace(MoveOnPlace(id, name, latitude, longitude, countryId))
        }

    }
}