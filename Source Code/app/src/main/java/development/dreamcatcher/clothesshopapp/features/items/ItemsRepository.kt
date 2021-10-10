package development.dreamcatcher.clothesshopapp.features.items

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import development.dreamcatcher.clothesshopapp.features.items.database.ItemDatabaseEntity
import development.dreamcatcher.clothesshopapp.features.items.database.ItemsDatabaseInteractor
import development.dreamcatcher.clothesshopapp.features.items.network.ItemsNetworkInteractor
import javax.inject.Inject

// Data Repository - the main gate of the model (features) part of the application
class ItemsRepository @Inject constructor(private val itemsNetworkInteractor: ItemsNetworkInteractor,
                                          private val databaseInteractor: ItemsDatabaseInteractor
) {

    fun getSingleSavedItemById(id: Int): LiveData<ItemDatabaseEntity>? {
        return databaseInteractor.getSingleSavedItemById(id)
    }

    fun getAllItems(backendUpdateRequired: Boolean): LiveData<List<ItemDatabaseEntity>>? {
        if (backendUpdateRequired) {
            updateDataFromBackEnd()
        }
        return databaseInteractor.getAllItems()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return itemsNetworkInteractor.networkError
    }

    @SuppressLint("CheckResult")
    private fun updateDataFromBackEnd() {

        itemsNetworkInteractor.getAllItems().subscribe {
            if (it.isSuccess && it.getOrDefault(null)?.size!! > 0) {

                // Clear database not to store outdated items
                databaseInteractor.clearDatabase()

                // Save freshly fetched items
                it.getOrNull()?.forEach {
                    databaseInteractor.addNewItem(it)
                }
            }
        }
    }
}