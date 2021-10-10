package development.dreamcatcher.clothesshopapp.features.wishlist
import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import development.dreamcatcher.clothesshopapp.features.wishlist.database.WishlistDatabaseInteractor
import development.dreamcatcher.clothesshopapp.features.wishlist.database.WishlistItemDatabaseEntity
import io.reactivex.Observable
import io.reactivex.subjects.SingleSubject
import javax.inject.Inject

// Data Repository - the main gate of the model (features) part of the application
class WishlistRepository @Inject constructor(private val databaseInteractor: WishlistDatabaseInteractor
) {

    fun getAllWishlistItems(): LiveData<List<WishlistItemDatabaseEntity>>? {
        return databaseInteractor.getWishlistItems()
    }

    /*fun getNetworkError(): LiveData<Boolean>? {
        return wishlistNetworkInteractor.networkError
    }*/

    @SuppressLint("CheckResult")
    fun addItemToWishlist(itemId: Int): Observable<Boolean> {
        val addItemSubject = SingleSubject.create<Boolean>()

        // Update database
        databaseInteractor.addNewItem(itemId)

        // Set observable value
        addItemSubject.onSuccess(true)

        return addItemSubject.toObservable()
    }

    @SuppressLint("CheckResult")
    fun removeItem(itemId: Int): Observable<Boolean> {
        val removeItemSubject = SingleSubject.create<Boolean>()

        // Update database
        databaseInteractor.removeItem(itemId)

        // Set observable value
        removeItemSubject.onSuccess(true)

        return removeItemSubject.toObservable()
    }
}