package pt.amn.moveon.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.amn.moveon.databinding.ViewHolderPlaceBinding
import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.presentation.viewmodels.CountryViewModel

class PlacesAdapter : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>(),
    ItemTouchHelperAdapter {

    private var places: MutableList<MoveOnPlace> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        return PlacesViewHolder(
            ViewHolderPlaceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.onBind(places[position])
    }

    override fun getItemCount(): Int {
        return places.size
    }

    fun bindPlaces(newPlaces: List<MoveOnPlace>) {
        places = newPlaces.toMutableList()
    }

    class PlacesViewHolder(private val binding: ViewHolderPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(place: MoveOnPlace) {

            binding.run {
                tvName.text = place.name
            }

        }

    }

    override fun onItemDismiss(viewModel: CountryViewModel, position: Int) {

        viewModel.removePlace(places.get(position))
        places.removeAt(position)
        notifyItemRemoved(position)
    }

}
