package com.example.bookreviewsver112.src.home.model

import Item
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class BestSellerResponse {
    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("link")
    @Expose
    private var link: String? = null

    @SerializedName("language")
    @Expose
    private var language: String? = null

    @SerializedName("copyright")
    @Expose
    private var copyright: String? = null

    @SerializedName("pubDate")
    @Expose
    private var pubDate: String? = null

    @SerializedName("imageUrl")
    @Expose
    private var imageUrl: String? = null

    @SerializedName("totalResults")
    @Expose
    private var totalResults: Int? = null

    @SerializedName("startIndex")
    @Expose
    private var startIndex: Int? = null

    @SerializedName("itemsPerPage")
    @Expose
    private var itemsPerPage: Int? = null

    @SerializedName("maxResults")
    @Expose
    private var maxResults: Int? = null

    @SerializedName("queryType")
    @Expose
    private var queryType: String? = null

    @SerializedName("searchCategoryId")
    @Expose
    private var searchCategoryId: String? = null

    @SerializedName("searchCategoryName")
    @Expose
    private var searchCategoryName: String? = null

    @SerializedName("returnCode")
    @Expose
    private var returnCode: String? = null

    @SerializedName("returnMessage")
    @Expose
    private var returnMessage: String? = null

    @SerializedName("item")
    @Expose
    private var item: List<Item?>? = null

}
