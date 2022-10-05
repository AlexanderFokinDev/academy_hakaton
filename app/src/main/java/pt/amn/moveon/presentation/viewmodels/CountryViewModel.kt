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
import pt.amn.moveon.domain.usecases.AddPlaceUseCase
import pt.amn.moveon.domain.usecases.GetPlacesUseCase
import pt.amn.moveon.domain.usecases.RemovePlaceUseCase
import pt.amn.moveon.domain.usecases.UpdateCountryUseCase
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: MoveOnRepository
) : ViewModel() {

    private val interactorCountries = UpdateCountryUseCase(repository)
    private val interactorGetPlaces = GetPlacesUseCase(repository)
    private val interactorAddPlace = AddPlaceUseCase(repository)
    private val interactorRemovePlace = RemovePlaceUseCase(repository)

    private lateinit var _mPlaces: LiveData<List<PlaceEntity>>
    val placesList : LiveData<List<PlaceEntity>> get() = _mPlaces

    private lateinit var country: Country

    fun setCountry(countrySelect: Country) {
        country = countrySelect
        viewModelScope.launch {
            _mPlaces = interactorGetPlaces.execute(country.id)
        }
    }

    fun changeVisitedFlagOfCountry(country: Country) {
        viewModelScope.launch {
            interactorCountries.execute(country)
        }
    }

    fun addPlace(id: String, latitude: Double, longitude: Double, name: String, countryId: Int) {
        viewModelScope.launch {
            interactorAddPlace.execute(MoveOnPlace(id, name, latitude, longitude, countryId))
        }
    }

    fun removePlace(place: MoveOnPlace) {
        viewModelScope.launch {
            interactorRemovePlace.execute(place)
        }
    }
}