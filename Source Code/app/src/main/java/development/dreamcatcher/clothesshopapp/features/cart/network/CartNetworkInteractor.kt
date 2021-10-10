package development.dreamcatcher.clothesshopapp.features.cart.network

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import javax.inject.Inject

// Interactor used for communication between features repository and external API
class CartNetworkInteractor @Inject constructor(var apiClient: CartRestClient) {

    val networkError: MutableLiveData<Boolean> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun addItemToCart(itemId: Int): Observable<Result<CartItemGsonObject>> {
        val cartItemSubject = SingleSubject.create<Result<CartItemGsonObject>>()

        apiClient.addItemToCart(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    cartItemSubject.onSuccess(Result.success(it))
                },
                {
                    networkError.postValue(true)
                    Log.e("getRepositories error: ", it.message)
                })

        return cartItemSubject.toObservable()
    }

    @SuppressLint("CheckResult")
    fun removeItem(itemId: Int): Observable<Result<Boolean>> {
        val cartItemSubject = SingleSubject.create<Result<Boolean>>()

        apiClient.removeItem(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    cartItemSubject.onSuccess(Result.success(true))
                },
                {
                    networkError.postValue(true)
                    Log.e("getRepositories error: ", it.message)
                })

        return cartItemSubject.toObservable()
    }
}