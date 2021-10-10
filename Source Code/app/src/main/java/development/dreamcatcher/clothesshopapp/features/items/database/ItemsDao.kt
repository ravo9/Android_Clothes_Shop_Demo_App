package development.dreamcatcher.clothesshopapp.features.items.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewItem(itemDatabaseEntity: ItemDatabaseEntity)

    @Query("SELECT * FROM items WHERE id = :id LIMIT 1")
    fun getSingleSavedItemById(id: Int): LiveData<ItemDatabaseEntity>?

    @Query("SELECT * FROM items")
    fun getAllSavedItems(): LiveData<List<ItemDatabaseEntity>>?

    @Query("DELETE FROM items")
    fun clearDatabase()
}