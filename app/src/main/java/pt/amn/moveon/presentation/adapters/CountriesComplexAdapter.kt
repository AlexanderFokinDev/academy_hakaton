package pt.amn.moveon.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.amn.moveon.databinding.ViewHolderCountryBinding
import pt.amn.moveon.domain.models.Country
import pt.amn.moveon.common.loadDrawableImage

class CountriesComplexAdapter(val context: Context, private val listener: OnRecyclerCountriesClicked)
    : RecyclerView.Adapter<CountriesComplexAdapter.CountriesViewHolder>() {

    private var countries: List<Country> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        return CountriesViewHolder(context,
            ViewHolderCountryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        holder.onBind(countries[position], listener)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    fun bindCountries(newCountries: List<Country>) {
        countries = newCountries
    }

    class CountriesViewHolder(val context: Context, private val binding: ViewHolderCountryBinding)
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

}

interface OnRecyclerCountriesClicked {
    fun onCountryClick(country: Country)
    fun onVisitedCheck(country: Country, visited: Boolean)
}