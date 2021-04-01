import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item {
    @SerializedName("itemId")
    @Expose
    var itemId: Int? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("pubDate")
    @Expose
    var pubDate: String? = null

    @SerializedName("priceStandard")
    @Expose
    var priceStandard: Int? = null

    @SerializedName("priceSales")
    @Expose
    var priceSales: Int? = null

    @SerializedName("discountRate")
    @Expose
    var discountRate: String? = null

    @SerializedName("saleStatus")
    @Expose
    var saleStatus: String? = null

    @SerializedName("mileage")
    @Expose
    var mileage: String? = null

    @SerializedName("mileageRate")
    @Expose
    var mileageRate: String? = null

    @SerializedName("coverSmallUrl")
    @Expose
    var coverSmallUrl: String? = null

    @SerializedName("coverLargeUrl")
    @Expose
    var coverLargeUrl: String? = null

    @SerializedName("categoryId")
    @Expose
    var categoryId: String? = null

    @SerializedName("categoryName")
    @Expose
    var categoryName: String? = null

    @SerializedName("publisher")
    @Expose
    var publisher: String? = null

    @SerializedName("customerReviewRank")
    @Expose
    var customerReviewRank: Double? = null

    @SerializedName("author")
    @Expose
    var author: String? = null

    @SerializedName("translator")
    @Expose
    var translator: String? = null

    @SerializedName("isbn")
    @Expose
    var isbn: String? = null

    @SerializedName("link")
    @Expose
    var link: String? = null

    @SerializedName("mobileLink")
    @Expose
    var mobileLink: String? = null

    @SerializedName("additionalLink")
    @Expose
    var additionalLink: String? = null

    @SerializedName("reviewCount")
    @Expose
    var reviewCount: Int? = null

    @SerializedName("rank")
    @Expose
    var rank: Int? = null


}