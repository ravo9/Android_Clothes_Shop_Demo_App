package development.dreamcatcher.clothesshopapp.features.wishlist.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(WishlistItemDatabaseEntity::class)], version = 1)
abstract class WishlistDatabase : RoomDatabase() {
    abstract fun getWishlistItemsDao(): WishlistItemsDao
}
