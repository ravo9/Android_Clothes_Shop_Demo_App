package development.dreamcatcher.clothesshopapp.features.cart.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemDatabaseEntity(
        @PrimaryKey
        val productId: Int,
        val cartId: Int
)
