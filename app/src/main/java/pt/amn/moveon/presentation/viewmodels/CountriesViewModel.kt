package pt.amn.moveon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.amn.moveon.data.local.CountryWithContinent
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.domain.repositories.MoveOnRepository
import pt.amn.moveon.domain.usecases.GetCountriesUseCase
import pt.amn.moveon.domain.usecases.UpdateCountryUseCase
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val repository: MoveOnRepository
) : ViewModel() {

    private val interactorGetCountries = GetCountriesUseCase(repository)
    private val interactorUpdateCountry = UpdateCountryUseCase(repository)

    private lateinit var _mAllCountries: LiveData<List<CountryWithContinent>>
    val countriesList : LiveData<List<CountryWithContinent>> get() = _mAllCountries

    init {
        viewModelScope.launch {
            _mAllCountries = interactorGetCountries.execute(onlyVisited = false)
        }
    }

    fun changeVisitedFlagOfCountry(country: Country) {
        viewModelScope.launch {
            interactorUpdateCountry.execute(country)
        }
    }
}