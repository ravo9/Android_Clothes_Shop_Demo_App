package development.dreamcatcher.clothesshopapp.features.items.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemDatabaseEntity(
        @PrimaryKey val id: Int,
        val name: String,
        val category: String,
        val price: Float,
        val oldPrice: Float?,
        val stock: Int?)

