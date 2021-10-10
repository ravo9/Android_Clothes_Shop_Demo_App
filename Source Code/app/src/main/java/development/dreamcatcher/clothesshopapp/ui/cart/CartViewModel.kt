package development.dreamcatcher.clothesshopapp.ui.cart

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import development.dreamcatcher.clothesshopapp.features.cart.CartRepository
import development.dreamcatcher.clothesshopapp.features.cart.database.CartItemDatabaseEntity
import development.dreamcatcher.clothesshopapp.features.items.ItemsRepository
import development.dreamcatcher.clothesshopapp.features.items.database.ItemDatabaseEntity
import io.reactivex.Observable
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val cartRepository: CartRepository
)
    : ViewModel(), LifecycleObserver {

    fun getWholeItems(backendUpdateRequired: Boolean): LiveData<List<ItemDatabaseEntity>>? {
        return itemsRepository.getAllItems(backendUpdateRequired)
    }

    fun getCartItems(): LiveData<List<CartItemDatabaseEntity>>? {
        return cartRepository.getAllCartItems()
    }

    fun removeItemById(id: Int): Observable<Boolean> {
        return cartRepository.removeItem(id)
    }
}