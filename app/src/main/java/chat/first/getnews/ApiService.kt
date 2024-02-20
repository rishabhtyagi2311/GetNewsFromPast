package chat.first.getnews

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val base_url = "https://api.nytimes.com/svc/archive/v1/"

private val retrofit  = Retrofit.Builder().baseUrl(base_url)
    .addConverterFactory(GsonConverterFactory.create()).build()


interface NewsApiService {
        @GET("{year}/{month}.json")
        suspend fun getNewsArchive(
            @Path("year") year: Int,
            @Path("month") month: Int,
            @Query("api-key") apiKey: String
        ): ApiResponse
}

open  class NewsRepository{
    private val newsApiService: NewsApiService = retrofit.create(NewsApiService::class.java)

    suspend fun getNewsArchive(year: Int , month:Int , apiKey:String): ApiResponse{
        return newsApiService.getNewsArchive(year , month , apiKey)
    }


}

