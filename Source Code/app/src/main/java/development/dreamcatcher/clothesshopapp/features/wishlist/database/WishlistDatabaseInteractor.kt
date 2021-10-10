package development.dreamcatcher.clothesshopapp.features.wishlist.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch

// Interactor used for communication between repository and internal database
class WishlistDatabaseInteractor(private val wishlistDatabase: WishlistDatabase) {

    fun addNewItem(itemId: Int): LiveData<Boolean> {
        val itemAddedLiveData = MutableLiveData<Boolean>()
        itemId.let {
            val wishlistItem =
                WishlistItemDatabaseEntity(
                    productId = it
                )
            launch {
                wishlistDatabase.getWishlistItemsDao().insertNewWishlistItem(wishlistItem)
            }
        }
        itemAddedLiveData.postValue(true)
        return itemAddedLiveData
    }

    fun removeItem(itemId: Int): LiveData<Boolean> {
        val itemAddedLiveData = MutableLiveData<Boolean>()
        itemId.let {
                launch {
                    wishlistDatabase.getWishlistItemsDao().removeItemById(itemId)
                }
            }
        itemAddedLiveData.postValue(true)
        return itemAddedLiveData
    }

    fun getWishlistItems(): LiveData<List<WishlistItemDatabaseEntity>>? {
        return wishlistDatabase.getWishlistItemsDao().getWishlistItems()
    }
}
