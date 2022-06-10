package pt.amn.moveon.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.amn.moveon.databinding.ViewHolderPlaceBinding
import pt.amn.moveon.domain.models.MoveOnPlace

class PlacesAdapter : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {

    private var places: List<MoveOnPlace> = listOf()

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
        places = newPlaces
    }

    class PlacesViewHolder(private val binding: ViewHolderPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(place: MoveOnPlace) {

            binding.run {
                tvName.text = place.name
            }

        }

    }

}
