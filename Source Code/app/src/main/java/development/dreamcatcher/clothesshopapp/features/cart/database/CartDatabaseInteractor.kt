package development.dreamcatcher.clothesshopapp.features.cart.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch

// Interactor used for communication between repository and internal database
class CartDatabaseInteractor(private val cartDatabase: CartDatabase) {

    fun addNewItem(item: CartItemDatabaseEntity): LiveData<Boolean> {
        val itemAddedLiveData = MutableLiveData<Boolean>()
        launch {
            cartDatabase.getCartItemsDao().insertNewCartItem(item)
        }
        itemAddedLiveData.postValue(true)
        return itemAddedLiveData
    }

    fun removeItem(itemId: Int): LiveData<Boolean> {
        val itemAddedLiveData = MutableLiveData<Boolean>()
        itemId.let {
                launch {
                    cartDatabase.getCartItemsDao().removeItemById(itemId)
                }
            }
        itemAddedLiveData.postValue(true)
        return itemAddedLiveData
    }

    fun getCartItems(): LiveData<List<CartItemDatabaseEntity>>? {
        return cartDatabase.getCartItemsDao().getCartItems()
    }
}
