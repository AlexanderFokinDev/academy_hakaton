package pt.amn.moveon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    // Variable data not available outside the class. You can change them only within this class
    private val _mutablePlacesList: MutableLiveData<Resource<List<MoveOnPlace>>> by lazy {
        MutableLiveData<Resource<List<MoveOnPlace>>>().also {
            viewModelScope.launch {
                interactor.getVisitedPlacesInCountry(country.id).also { result ->
                    when (result.isError) {
                        true -> _mutablePlacesList.postValue(
                            Resource.error(
                                result.description, result.dataList
                            )
                        )
                        false -> _mutablePlacesList.postValue(Resource.success(result.dataList))
                    }
                }
            }
        }
    }

    // A variable of the LiveData type will be available outside, you can only subscribe to it,
    // you cannot change the data stored inside
    val placesList: LiveData<Resource<List<MoveOnPlace>>> get() = _mutablePlacesList

    private lateinit var country: Country

    fun setCountry(countrySelect: Country) {
        country = countrySelect
    }

    fun changeVisitedFlagOfCountry(country: Country, visited: Boolean) {
        viewModelScope.launch {
            interactor.changeVisitedFlagOfCountry(country, visited)
        }
    }

    fun addPlace(id: String, latitude: Double, longitude: Double, name: String, countryId: Int) {
        viewModelScope.launch {
            interactor.addPlace(MoveOnPlace(id, name, latitude, longitude, countryId))


            MutableLiveData<Resource<List<MoveOnPlace>>>().also {
                viewModelScope.launch {
                    interactor.getVisitedPlacesInCountry(country.id).also { result ->
                        when (result.isError) {
                            true -> _mutablePlacesList.postValue(
                                Resource.error(
                                    result.description, result.dataList
                                )
                            )
                            false -> _mutablePlacesList.postValue(Resource.success(result.dataList))
                        }
                    }
                }
            }

        }

    }
}