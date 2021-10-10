package development.dreamcatcher.clothesshopapp.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import development.dreamcatcher.clothesshopapp.ui.cart.CartViewModel
import development.dreamcatcher.clothesshopapp.ui.detailedview.DetailedViewViewModel
import development.dreamcatcher.clothesshopapp.ui.feed.FeedViewModel
import development.dreamcatcher.clothesshopapp.ui.wishlist.WishlistViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    internal abstract fun bindFeedViewModel(feedViewModel: FeedViewModel)
            : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailedViewViewModel::class)
    internal abstract fun bindDetailedViewViewModel(detailedViewViewModel: DetailedViewViewModel)
            : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    internal abstract fun bindCartViewModel(cartViewModel: CartViewModel)
            : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WishlistViewModel::class)
    internal abstract fun bindWishlistViewModel(wishlistViewModel: WishlistViewModel)
            : ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory)
            : ViewModelProvider.Factory
}