package pt.amn.moveon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.repositories.MoveOnRepository
import pt.amn.moveon.domain.usecases.GetCountriesUseCase
import pt.amn.moveon.presentation.viewmodels.utils.Resource
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val repository: MoveOnRepository
) : ViewModel() {

    private val interactor = GetCountriesUseCase(repository)

    // Variable data not available outside the class. You can change them only within this class
    private val _mutableCountriesList: MutableLiveData<Resource<List<Country>>> by lazy {
        MutableLiveData<Resource<List<Country>>>().also {
            viewModelScope.launch {
                interactor.getCountries().also { result ->
                    when (result.isError) {
                        true -> _mutableCountriesList.postValue(Resource.error(result.description
                            , result.dataList))
                        false -> _mutableCountriesList.postValue(Resource.success(result.dataList))
                    }
                }
            }
        }
    }

    // A variable of the LiveData type will be available outside, you can only subscribe to it,
    // you cannot change the data stored inside
    val countriesList: LiveData<Resource<List<Country>>> get() = _mutableCountriesList

    fun changeVisitedFlagOfCountry(country: Country, visited: Boolean) {
        viewModelScope.launch {
            interactor.changeVisitedFlagOfCountry(country, visited)
        }
    }
}