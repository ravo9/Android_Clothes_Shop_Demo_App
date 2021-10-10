package development.dreamcatcher.clothesshopapp.injection

import dagger.Component
import development.dreamcatcher.clothesshopapp.features.cart.network.CartNetworkInteractor
import development.dreamcatcher.clothesshopapp.features.items.database.ItemsDatabaseInteractor
import development.dreamcatcher.clothesshopapp.features.items.network.ItemsNetworkInteractor
import development.dreamcatcher.clothesshopapp.features.wishlist.database.WishlistDatabaseInteractor
import development.dreamcatcher.clothesshopapp.ui.cart.CartFragment
import development.dreamcatcher.clothesshopapp.ui.cart.CartViewModel
import development.dreamcatcher.clothesshopapp.ui.detailedview.DetailedViewFragment
import development.dreamcatcher.clothesshopapp.ui.detailedview.DetailedViewViewModel
import development.dreamcatcher.clothesshopapp.ui.feed.FeedActivity
import development.dreamcatcher.clothesshopapp.ui.feed.FeedViewModel
import development.dreamcatcher.clothesshopapp.ui.wishlist.WishlistFragment
import development.dreamcatcher.clothesshopapp.ui.wishlist.WishlistViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, FeedModule::class, ViewModelModule::class))
interface MainComponent {
    fun inject(feedActivity: FeedActivity)
    fun inject(detailedViewFragment: DetailedViewFragment)
    fun inject(cartViewFragment: CartFragment)
    fun inject(wishlistFragment: WishlistFragment)
    fun inject(feedViewModel: FeedViewModel)
    fun inject(detailedViewViewModel: DetailedViewViewModel)
    fun inject(cartViewModel: CartViewModel)
    fun inject(wishlistViewModel: WishlistViewModel)
    fun inject(itemsNetworkInteractor: ItemsNetworkInteractor)
    fun inject(itemsDatabaseInteractor: ItemsDatabaseInteractor)
    fun inject(cartNetworkInteractor: CartNetworkInteractor)
    fun inject(cartDatabaseInteractor: WishlistDatabaseInteractor)
}