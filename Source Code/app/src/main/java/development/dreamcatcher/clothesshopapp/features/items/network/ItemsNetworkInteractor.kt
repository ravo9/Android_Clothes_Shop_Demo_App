package development.dreamcatcher.clothesshopapp.features.items.network

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import javax.inject.Inject

// Interactor used for communication between features repository and external API
class ItemsNetworkInteractor @Inject constructor(var restClient: RestClient) {

    val networkError: MutableLiveData<Boolean> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getAllItems(): Observable<Result<List<ItemGsonObject>>> {
        val allItemsSubject = SingleSubject.create<Result<List<ItemGsonObject>>>()

        restClient.getItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    allItemsSubject.onSuccess(Result.success(it))
                },
                {
                    networkError.postValue(true)
                    Log.e("getRepositories error: ", it.message)
                })

        return allItemsSubject.toObservable()
    }
}