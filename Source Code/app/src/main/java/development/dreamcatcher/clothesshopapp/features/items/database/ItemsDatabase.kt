package development.dreamcatcher.clothesshopapp.features.items.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(ItemDatabaseEntity::class)], version = 1)
abstract class ItemsDatabase : RoomDatabase() {
    abstract fun getItemsDao(): ItemsDao
}