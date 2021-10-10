package development.dreamcatcher.clothesshopapp.features.wishlist.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist_items")
data class WishlistItemDatabaseEntity(
        @PrimaryKey val productId: Int
)
