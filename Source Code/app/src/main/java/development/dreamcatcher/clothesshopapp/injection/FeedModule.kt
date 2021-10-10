package development.dreamcatcher.clothesshopapp.injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import development.dreamcatcher.clothesshopapp.features.cart.CartRepository
import development.dreamcatcher.clothesshopapp.features.cart.database.CartDatabase
import development.dreamcatcher.clothesshopapp.features.cart.database.CartDatabaseInteractor
import development.dreamcatcher.clothesshopapp.features.cart.network.CartNetworkAdapter
import development.dreamcatcher.clothesshopapp.features.cart.network.CartNetworkInteractor
import development.dreamcatcher.clothesshopapp.features.cart.network.CartRestClient
import development.dreamcatcher.clothesshopapp.features.items.ItemsRepository
import development.dreamcatcher.clothesshopapp.features.items.database.ItemsDatabase
import development.dreamcatcher.clothesshopapp.features.items.database.ItemsDatabaseInteractor
import development.dreamcatcher.clothesshopapp.features.items.network.ItemsNetworkInteractor
import development.dreamcatcher.clothesshopapp.features.items.network.NetworkAdapter
import development.dreamcatcher.clothesshopapp.features.items.network.RestClient
import development.dreamcatcher.clothesshopapp.features.wishlist.WishlistRepository
import development.dreamcatcher.clothesshopapp.features.wishlist.database.WishlistDatabase
import development.dreamcatcher.clothesshopapp.features.wishlist.database.WishlistDatabaseInteractor
import javax.inject.Singleton

@Module
class FeedModule {

    // Items section
    @Provides
    @Singleton
    fun providesDatabase(application: Context): ItemsDatabase {
        return Room.databaseBuilder(application, ItemsDatabase::class.java, "main_database").build()
    }

    @Provides
    @Singleton
    fun providesItemsDatabaseInteractor(itemsDatabase: ItemsDatabase): ItemsDatabaseInteractor {
        return ItemsDatabaseInteractor(
            itemsDatabase
        )
    }

    @Provides
    @Singleton
    fun providesItemsNetworkInteractor(restClient: RestClient): ItemsNetworkInteractor {
        return ItemsNetworkInteractor(
            restClient
        )
    }

    @Provides
    @Singleton
    fun providesRestClient(): RestClient {
        return NetworkAdapter.apiClient()
    }

    @Provides
    @Singleton
    fun providesItemsRepository(ItemsNetworkInteractor: ItemsNetworkInteractor,
                                itemsDatabaseInteractor: ItemsDatabaseInteractor
    ): ItemsRepository {
        return ItemsRepository(
            ItemsNetworkInteractor,
            itemsDatabaseInteractor
        )
    }


    // Cart section
    @Provides
    @Singleton
    fun providesCartItemsDatabase(application: Context): CartDatabase {
        return Room.databaseBuilder(application, CartDatabase::class.java, "cart_database").build()
    }

    @Provides
    @Singleton
    fun providesCartDatabaseInteractor(cartDatabase: CartDatabase): CartDatabaseInteractor {
        return CartDatabaseInteractor(
            cartDatabase
        )
    }

    @Provides
    @Singleton
    fun providesCartNetworkInteractor(cartRestClient: CartRestClient): CartNetworkInteractor {
        return CartNetworkInteractor(
            cartRestClient
        )
    }

    @Provides
    @Singleton
    fun providesCartRestClient(): CartRestClient {
        return CartNetworkAdapter.cartRestClient()
    }

    @Provides
    @Singleton
    fun providesCartRepository(cartNetworkInteractor: CartNetworkInteractor,
                                cartDatabaseInteractor: CartDatabaseInteractor
    ): CartRepository {
        return CartRepository(
            cartNetworkInteractor,
            cartDatabaseInteractor
        )
    }


    // Wishlist section
    @Provides
    @Singleton
    fun providesWishlistItemsDatabase(application: Context): WishlistDatabase {
        return Room.databaseBuilder(application, WishlistDatabase::class.java, "wishlist_database").build()
    }

    @Provides
    @Singleton
    fun providesWishlistDatabaseInteractor(wishlistDatabase: WishlistDatabase): WishlistDatabaseInteractor {
        return WishlistDatabaseInteractor(
            wishlistDatabase
        )
    }

    @Provides
    @Singleton
    fun providesWishlistRepository(wishlistDatabaseInteractor: WishlistDatabaseInteractor
    ): WishlistRepository {
        return WishlistRepository(
            wishlistDatabaseInteractor
        )
    }
}