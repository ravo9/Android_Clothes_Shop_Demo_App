package development.dreamcatcher.clothesshopapp.ui.feed

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import development.dreamcatcher.clothesshopapp.features.cart.CartRepository
import development.dreamcatcher.clothesshopapp.features.items.ItemsRepository
import development.dreamcatcher.clothesshopapp.features.items.database.ItemDatabaseEntity
import development.dreamcatcher.clothesshopapp.features.wishlist.WishlistRepository
import io.reactivex.Observable
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val cartRepository: CartRepository,
    private val wishlistRepository: WishlistRepository
)
    : ViewModel(), LifecycleObserver {

    fun getAllItems(backendUpdateRequired: Boolean): LiveData<List<ItemDatabaseEntity>>? {
        return itemsRepository.getAllItems(backendUpdateRequired)
    }

    /*fun getAllItems(backendUpdateRequired: Boolean): LiveData<List<ItemDatabaseEntity>>? {
        return itemsRepository.getAllItems(backendUpdateRequired)
    }*/

    fun getNetworkError(): LiveData<Boolean>? {
        return itemsRepository.getNetworkError()
    }

    fun addItemToCart(itemId: Int): Observable<Boolean> {
        return cartRepository.addItemToCart(itemId)
    }

    fun addItemToWishlist(itemId: Int): Observable<Boolean> {
        return wishlistRepository.addItemToWishlist(itemId)
    }
}