package chat.first.getnews


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEntries(navController: NavHostController)
{
    Box(modifier = Modifier.fillMaxSize())
    {
        var expandedState by remember { mutableStateOf(false) }
        val rotationState by animateFloatAsState(targetValue =
        if(expandedState) 180f else 0f)
        var year by remember { mutableStateOf(0) }
        var month by remember { mutableStateOf(0) }
        val focusManager = LocalFocusManager.current
        val context = LocalContext.current

        Column(
            modifier = Modifier.fillMaxSize()
                .clickable { focusManager.clearFocus() },
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        )
                    )
                    .padding(12.dp)
                    .border(2.dp, Color.DarkGray, RoundedCornerShape(4.dp)),
                shape = RoundedCornerShape(4),
                onClick ={
                    expandedState = !expandedState
                }
            )
            {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp))
                {
                    Row(verticalAlignment = Alignment.CenterVertically){

                        Text(text = "News Search Guidelines",
                            modifier =Modifier.weight(6f),
                            fontWeight = FontWeight.Bold ,
                            maxLines =1,
                            fontSize = 20.sp,
                            overflow = TextOverflow.Ellipsis)
                        IconButton(modifier = Modifier
                            .alpha(0.5F)
                            .weight(1f)
                            .rotate(rotationState),

                            onClick = {
                                expandedState = !expandedState
                            }) {
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Drop-Down Arrow")


                        }

                    }
                    if(expandedState)
                    {
                        Text(
                            text ="Enter the year and month to find news articles. Year should be 4 digits (e.g., 2023), and month should be a number from 1 to 12.\n" +
                                    "\n" +
                                    "Notes:\n" +
                                    "\n" +
                                    "* Max past capacity: 1857.\n" +
                                    "* Top 10 popular articles available.",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(
                value = year.toString(),
                onValueChange = { year = it.toIntOrNull()!! },
                label = { Text("Enter Year") },
                singleLine = true,

                modifier = Modifier.fillMaxWidth().padding(12.dp),

            )
            OutlinedTextField(
                value = month.toString(),
                onValueChange = { month = it.toIntOrNull()!! },
                label = { Text("Enter Month") },
                singleLine = true,

                modifier = Modifier.fillMaxWidth().padding(12.dp),

                )

            Button(onClick = {navController.navigate(Screens.NewsCollection.pass_details(year , month))}) {
                    Text("Get News")
            }
        }
    }
}