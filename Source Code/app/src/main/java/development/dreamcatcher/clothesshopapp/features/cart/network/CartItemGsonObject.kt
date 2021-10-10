package development.dreamcatcher.clothesshopapp.features.cart.network

import com.google.gson.annotations.SerializedName

// ApiResponse object used for deserializing features coming from API endpoint
data class CartItemGsonObject(

    @SerializedName("productId")
    val productId: Int?,

    @SerializedName("cartId")
    val cartId: Int?
)