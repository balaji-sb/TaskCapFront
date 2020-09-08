package com.pb.taskcapfront.model.response.dashboard

import com.google.gson.annotations.SerializedName
import com.pb.taskcapfront.enums.SectionType

/**
 * Created by balaji on 7/9/20 10:28 PM
 */


data class APIResponse(

    @SerializedName("code")
    val code: String,

    @SerializedName("content")
    val contents: List<Contents>
)

data class Contents(

    @SerializedName("sectionType")
    val sectionType: SectionType,

    @SerializedName("name")
    val name: String,

    @SerializedName("bannerImage")
    val bannerImage: String = "",

    @SerializedName("firstImage")
    val firstImage: String = "",

    @SerializedName("secondImage")
    val secondImage: String = "",

    @SerializedName("products")
    val products: List<Products>
)

data class Products(

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("imageURL")
    val image: String,

    @SerializedName("type")
    val type: String
)