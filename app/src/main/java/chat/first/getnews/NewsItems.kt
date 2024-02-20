package chat.first.getnews

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiResponse(
    val response : Response
) : Parcelable

@Parcelize
data class Response(
    val docs : List<Article>
): Parcelable

@Parcelize
data class Article(
    val headline : Headlines,
    val web_url: String,
    val lead_paragraph:String,
    val multimedia : List<Multimedia>,
    val pub_date : String
) : Parcelable

@Parcelize
data class Headlines(
    val main : String
): Parcelable
@Parcelize
data class Multimedia(
    val url : String
): Parcelable
