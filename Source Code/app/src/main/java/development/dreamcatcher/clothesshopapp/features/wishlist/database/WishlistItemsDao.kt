package development.dreamcatcher.clothesshopapp.features.wishlist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WishlistItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewWishlistItem(wishlistItemDatabaseEntity: WishlistItemDatabaseEntity)

    @Query("SELECT * FROM wishlist_items")
    fun getWishlistItems(): LiveData<List<WishlistItemDatabaseEntity>>?

    @Query("DELETE FROM wishlist_items WHERE productId = :id")
    fun removeItemById(id: Int)
}
