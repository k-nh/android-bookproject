package com.example.bookreviewsver.src.Main.Home.model

data class BestSellerData(var itemId:Int?,
                          var rank: Int?,
                          var coverImgUrl: String?,
                          var title: String?,
                          var author: String?,
                          var publisher: String?,
                          var description :String?,
                          var pubDate: String?,
                          var favorite: Boolean?,
                          var categoryId: Int?,
                          var link: String)
