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
class WorkplaceViewModel @Inject constructor(
    private val repository: MoveOnRepository
) : ViewModel() {

    private val interactor = GetCountriesUseCase(repository)

    // Variable data not available outside the class. You can change them only within this class
    private val _mutableVisitedCountries: MutableLiveData<Resource<List<Country>>> by lazy {
        MutableLiveData<Resource<List<Country>>>().also {
            viewModelScope.launch {
                interactor.getVisitedCountries().also { result ->
                    when (result.isError) {
                        true -> _mutableVisitedCountries.postValue(Resource.error(result.description,
                            result.dataList))
                        false -> _mutableVisitedCountries.postValue(Resource.success(result.dataList))
                    }
                }
            }
        }
    }

    // A variable of the LiveData type will be available outside, you can only subscribe to it,
    // you cannot change the data stored inside
    val visitedCountries: LiveData<Resource<List<Country>>> get() = _mutableVisitedCountries

}