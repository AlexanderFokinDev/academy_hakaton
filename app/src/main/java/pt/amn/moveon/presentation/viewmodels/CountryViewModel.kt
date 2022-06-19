package pt.amn.moveon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.amn.moveon.data.local.PlaceEntity
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.domain.repositories.MoveOnRepository
import pt.amn.moveon.domain.usecases.CountriesUseCase
import pt.amn.moveon.domain.usecases.PlacesUseCase
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: MoveOnRepository
) : ViewModel() {

    private val interactorCountries = CountriesUseCase(repository)
    private val interactorPlaces = PlacesUseCase(repository)

    private lateinit var _mPlaces: LiveData<List<PlaceEntity>>
    val placesList : LiveData<List<PlaceEntity>> get() = _mPlaces

    private lateinit var country: Country

    fun setCountry(countrySelect: Country) {
        country = countrySelect
        viewModelScope.launch {
            _mPlaces = interactorPlaces.getVisitedPlacesInCountry(country.id)
        }
    }

    fun changeVisitedFlagOfCountry(country: Country, visited: Boolean) {
        viewModelScope.launch {
            interactorCountries.changeVisitedFlagOfCountry(country, visited)
        }
    }

    fun addPlace(id: String, latitude: Double, longitude: Double, name: String, countryId: Int) {
        viewModelScope.launch {
            interactorPlaces.addPlace(MoveOnPlace(id, name, latitude, longitude, countryId))
        }
    }

    fun removePlace(place: MoveOnPlace) {
        viewModelScope.launch {
            interactorPlaces.removePlace(place)
        }
    }
}