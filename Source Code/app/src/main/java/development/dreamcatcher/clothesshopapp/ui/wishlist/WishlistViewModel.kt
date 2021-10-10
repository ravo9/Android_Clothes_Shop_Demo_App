package development.dreamcatcher.clothesshopapp.ui.wishlist

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import development.dreamcatcher.clothesshopapp.features.items.ItemsRepository
import development.dreamcatcher.clothesshopapp.features.items.database.ItemDatabaseEntity
import development.dreamcatcher.clothesshopapp.features.wishlist.WishlistRepository
import development.dreamcatcher.clothesshopapp.features.wishlist.database.WishlistItemDatabaseEntity
import io.reactivex.Observable
import javax.inject.Inject

class WishlistViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val wishlistRepository: WishlistRepository
)
    : ViewModel(), LifecycleObserver {

    fun getWholeItems(backendUpdateRequired: Boolean): LiveData<List<ItemDatabaseEntity>>? {
        return itemsRepository.getAllItems(backendUpdateRequired)
    }

    fun getWishlistItems(): LiveData<List<WishlistItemDatabaseEntity>>? {
        return wishlistRepository.getAllWishlistItems()
    }

    fun removeItemById(id: Int): Observable<Boolean> {
        return wishlistRepository.removeItem(id)
    }
}