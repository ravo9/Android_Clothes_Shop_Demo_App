package development.dreamcatcher.clothesshopapp.features.cart.network

import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

// External gate for communication with API endpoints (operated by Retrofit)
interface CartRestClient {

    @POST("/cart")
    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    fun addItemToCart(
        @Field("productId") productId: Int
    ): Observable<CartItemGsonObject>

    @DELETE("/cart/{cartId}")
    fun removeItem(@Path("cartId") id: Int): Completable
}