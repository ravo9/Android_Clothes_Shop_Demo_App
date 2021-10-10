package development.dreamcatcher.clothesshopapp.features.cart.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewCartItem(cartItemDatabaseEntity: CartItemDatabaseEntity)

    @Query("SELECT * FROM cart_items")
    fun getCartItems(): LiveData<List<CartItemDatabaseEntity>>?

    @Query("DELETE FROM cart_items WHERE productId = :id")
    fun removeItemById(id: Int)

    @Query("DELETE FROM cart_items")
    fun clearDatabase()
}
