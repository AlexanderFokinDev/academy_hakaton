package pt.amn.moveon.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.amn.moveon.R
import pt.amn.moveon.databinding.ViewHolderCountryBinding
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.common.loadDrawableImage
import pt.amn.moveon.databinding.ViewHolderContinentBinding
import pt.amn.moveon.domain.models.Continent
import pt.amn.moveon.domain.models.PartOfTheWorld
import java.lang.IllegalArgumentException

class CountriesComplexAdapter(val context: Context, private val listener: OnRecyclerCountriesClicked)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var countriesAndContinents: List<PartOfTheWorld> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {

            R.layout.view_holder_continent -> {
                return ContinentViewHolder(
                    context, ViewHolderContinentBinding.inflate(
                        inflater,
                        parent, false
                    )
                )
            }

            else -> {
                return CountryViewHolder(
                    context, ViewHolderCountryBinding.inflate(
                        inflater,
                        parent, false
                    )
                )
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            R.layout.view_holder_continent -> {
                val continentHolder = holder as ContinentViewHolder
                continentHolder.onBind(countriesAndContinents[position] as Continent, listener)
            }
            R.layout.view_holder_country -> {
                val countryHolder = holder as CountryViewHolder
                countryHolder.onBind(countriesAndContinents[position] as Country, listener)
            }
        }
    }

    override fun getItemCount(): Int {
        return countriesAndContinents.size
    }

    fun bindCountries(newCountriesAndContinents: List<PartOfTheWorld>) {
        countriesAndContinents = newCountriesAndContinents
    }

    class CountryViewHolder(val context: Context, private val binding: ViewHolderCountryBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun onBind(country: Country, listener: OnRecyclerCountriesClicked) {
            binding.run {
                tvCountry.text = country.getLocalName()
                cbCheck.isChecked = country.visited
                ivFlag.loadDrawableImage(context, binding.root, country.flagResId)

                cbCheck.setOnClickListener{
                    listener.onVisitedCheck(country, cbCheck.isChecked)
                }

                tvCountry.setOnClickListener {
                    listener.onCountryClick(country)
                }

                ivFlag.setOnClickListener {
                    listener.onCountryClick(country)
                }
            }
        }

    }

    class ContinentViewHolder(val context: Context, private val binding: ViewHolderContinentBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun onBind(continent: Continent, listener: OnRecyclerCountriesClicked) {
            binding.run {
                tvContinent.text = continent.getLocalName()
            }
        }

    }

    override fun getItemViewType(position: Int): Int {

        if (countriesAndContinents[position] is Continent) {
            return R.layout.view_holder_continent
        } else if (countriesAndContinents[position] is Country) {
            return R.layout.view_holder_country
        } else {
            throw IllegalArgumentException("An item in the list should be a country ot a continent")
        }
    }


}

interface OnRecyclerCountriesClicked {
    fun onCountryClick(country: Country)
    fun onVisitedCheck(country: Country, visited: Boolean)
}