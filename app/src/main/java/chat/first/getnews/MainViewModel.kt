package chat.first.getnews

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

private const val api = "GSkWA7fx8DMVszjXVoHSLa0rcqxWvAbf"
class MainViewModel : ViewModel() {


    private val _screenState = mutableStateOf(NewsState())
    val screenState : State<NewsState> = _screenState
    private val newsRepository = NewsRepository()
    fun fetchNews(
        year:Int,
        month: Int
    )
    {
        println("Fetching news articles...")

        viewModelScope.launch {
            try{
                val response = newsRepository.getNewsArchive(year , month , api).response.docs.take(10)
                    _screenState.value = _screenState.value.copy(
                    list = response,
                    loading = false,
                    error = null


                )
                println("News articles fetched: ${response.size}")

            } catch (e : Exception)
            {
                _screenState.value = _screenState.value.copy(
                    loading = false,
                    error = "Error fetching News Articles ${e.message}"
                )
            }
        }
    }

}

data class NewsState(
    val loading: Boolean = true,
    val list: List<Article> = emptyList(),
    val error: String? = null
)