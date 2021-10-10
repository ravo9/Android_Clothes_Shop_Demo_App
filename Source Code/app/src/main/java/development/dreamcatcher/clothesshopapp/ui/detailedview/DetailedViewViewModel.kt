package development.dreamcatcher.clothesshopapp.ui.detailedview

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import development.dreamcatcher.clothesshopapp.features.items.ItemsRepository
import development.dreamcatcher.clothesshopapp.features.items.database.ItemDatabaseEntity
import javax.inject.Inject

class DetailedViewViewModel @Inject constructor(private val itemsRepository: ItemsRepository)
    : ViewModel(), LifecycleObserver {

    fun getSingleSavedItemById(id: Int): LiveData<ItemDatabaseEntity>? {
        return itemsRepository.getSingleSavedItemById(id)
    }
}