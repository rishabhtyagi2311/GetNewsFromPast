package chat.first.getnews

sealed class Screens(val route : String) {
    object Home : Screens("home_screen")
    object UserEntry : Screens("entries_screen")
    object NewsCollection : Screens("collections_screen/{year}/{month}")
    {
        fun pass_details(year : Int , month : Int) = "collections_screen/$year/$month"
    }

}