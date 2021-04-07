package com.example.bookreviewsver112.src.Main.home.model

import android.media.ThumbnailUtils

data class BestSellerData(var itemId:Int?,
                          var rank: Int?,
                          var coverImgUrl: String?,
                          var title: String?,
                          var author: String?,
                          var publisher: String?,
                          var description :String?,
                          var pubDate: String?,
                          var favorite: Boolean?){

}
