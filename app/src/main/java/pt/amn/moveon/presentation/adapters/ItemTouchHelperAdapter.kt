package pt.amn.moveon.presentation.adapters

import pt.amn.moveon.presentation.viewmodels.CountryViewModel

interface ItemTouchHelperAdapter {

    /**
     * Called when an item has been dismissed by a swipe.
     * Implementations should call after
     * adjusting the underlying data to reflect this removal.
     *
     * @param position The position of the item dismissed.
     *
     */
    fun onItemDismiss(viewModel: CountryViewModel, position: Int)
}