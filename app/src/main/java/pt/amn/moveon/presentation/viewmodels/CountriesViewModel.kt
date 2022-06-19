package pt.amn.moveon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.amn.moveon.data.local.CountryEntity
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.repositories.MoveOnRepository
import pt.amn.moveon.domain.usecases.CountriesUseCase
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val repository: MoveOnRepository
) : ViewModel() {

    private val interactor = CountriesUseCase(repository)

    private lateinit var _mAllCountries: LiveData<List<CountryEntity>>
    val countriesList : LiveData<List<CountryEntity>> get() = _mAllCountries

    init {
        viewModelScope.launch {
            _mAllCountries = interactor.getCountries()
        }
    }

    fun changeVisitedFlagOfCountry(country: Country, visited: Boolean) {
        viewModelScope.launch {
            interactor.changeVisitedFlagOfCountry(country, visited)
        }
    }
}