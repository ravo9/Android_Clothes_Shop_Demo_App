package development.dreamcatcher.clothesshopapp.features.items.network

import com.google.gson.annotations.SerializedName

// ApiResponse object used for deserializing features coming from API endpoint
data class ItemGsonObject(

    @SerializedName("productId")
    val productId: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("category")
    val category: String?,

    @SerializedName("price")
    val price: Float?,

    @SerializedName("oldPrice")
    val oldPrice: Float?,

    @SerializedName("stock")
    val stock: Int?
)