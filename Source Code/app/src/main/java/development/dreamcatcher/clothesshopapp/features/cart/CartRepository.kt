package development.dreamcatcher.clothesshopapp.features.cart

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import development.dreamcatcher.clothesshopapp.features.cart.database.CartDatabaseInteractor
import development.dreamcatcher.clothesshopapp.features.cart.database.CartItemDatabaseEntity
import development.dreamcatcher.clothesshopapp.features.cart.network.CartNetworkInteractor
import io.reactivex.Observable
import io.reactivex.subjects.SingleSubject
import javax.inject.Inject

// Data Repository - the main gate of the model (features) part of the application
class CartRepository @Inject constructor(private val cartNetworkInteractor: CartNetworkInteractor,
                                         private val databaseInteractor: CartDatabaseInteractor
) {

    fun getAllCartItems(): LiveData<List<CartItemDatabaseEntity>>? {
        return databaseInteractor.getCartItems()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return cartNetworkInteractor.networkError
    }

    @SuppressLint("CheckResult")
    fun addItemToCart(itemId: Int): Observable<Boolean> {
        val addItemSubject = SingleSubject.create<Boolean>()

        cartNetworkInteractor.addItemToCart(itemId).subscribe {
            it.onSuccess {

                    // Update database
                    // Temporary we cannot use returned itemId because of the API issue.
                    if (it.cartId != null) {
                        val item = CartItemDatabaseEntity(
                            itemId,
                            it.cartId
                        )
                        databaseInteractor.addNewItem(item)

                        // Set observable value
                        addItemSubject.onSuccess(true)
                    }
                }
            it.onFailure {
                Log.e("addItemToCart error: ", it.message)
            }
        }

        return addItemSubject.toObservable()
    }

    @SuppressLint("CheckResult")
    fun removeItem(itemId: Int): Observable<Boolean> {
        val removeItemSubject = SingleSubject.create<Boolean>()

        // Update database
        //databaseInteractor.removeItem(itemId)

        // Set observable value
        //removeItemSubject.onSuccess(true)

        cartNetworkInteractor.removeItem(itemId).subscribe {
            it.onSuccess {

                // Update database
                databaseInteractor.removeItem(itemId)

                // Set observable value
                removeItemSubject.onSuccess(true)
            }
            it.onFailure {
                Log.e("addItemToCart error: ", it.message)
            }
        }

        return removeItemSubject.toObservable()
    }
}