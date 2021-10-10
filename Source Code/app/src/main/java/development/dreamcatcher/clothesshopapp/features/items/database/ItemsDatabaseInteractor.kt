package development.dreamcatcher.clothesshopapp.features.items.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.clothesshopapp.features.items.network.ItemGsonObject
import kotlinx.coroutines.launch

// Interactor used for communication between repository and internal database
class ItemsDatabaseInteractor(private val itemsDatabase: ItemsDatabase) {

    fun addNewItem(item: ItemGsonObject?): LiveData<Boolean> {

        val saveItemLiveData = MutableLiveData<Boolean>()

        item?.let {
            if (it.productId != null && it.name != null && it.category != null && it.price != null) {
                val itemEntity =
                    ItemDatabaseEntity(
                        id = it.productId,
                        name = it.name,
                        category = it.category,
                        price = it.price,
                        oldPrice = it.oldPrice,
                        stock = it.stock
                    )
                launch {
                    itemsDatabase.getItemsDao().insertNewItem(itemEntity)
                }
            }
        }
        saveItemLiveData.postValue(true)
        return saveItemLiveData
    }

    fun getSingleSavedItemById(id: Int): LiveData<ItemDatabaseEntity>? {
        return itemsDatabase.getItemsDao().getSingleSavedItemById(id)
    }

    fun getAllItems(): LiveData<List<ItemDatabaseEntity>>? {
        return itemsDatabase.getItemsDao().getAllSavedItems()
    }

    fun clearDatabase() {
        launch {
            itemsDatabase.getItemsDao().clearDatabase()
        }
    }
}