package com.pb.taskcapfront.enums

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by balaji on 7/9/20 10:36 PM
 */


enum class SectionType {

    @SerializedName("horizontalFreeScroll")
    HORIZONTAL_FREE_SCROLL,

    @SerializedName("banner")
    BANNER,

    @SerializedName("splitBanner")
    SPLIT_BANNER,

}