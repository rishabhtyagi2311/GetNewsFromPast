package chat.first.getnews

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun Setup(navController: NavHostController)
{
    NavHost(navController = navController, startDestination = Screens.Home.route)
    {

        composable(
            route = Screens.Home.route
        )
        {
            Home(navController)
        }
        composable(
            route = Screens.UserEntry.route,


        ){

            UserEntries(navController)
        }
        composable(
            route = Screens.NewsCollection.route,
            arguments = listOf(
                navArgument("year"){type = NavType.IntType},
                navArgument("month"){type = NavType.IntType}
            )
        )
        {
                backStackEntry ->
            val year = backStackEntry.arguments?.getInt("year")
            val month  = backStackEntry.arguments?.getInt("month")
            if (year != null) {
                if (month != null) {
                    DisplayNews(navController , year , month)
                }
            }
        }
    }
}