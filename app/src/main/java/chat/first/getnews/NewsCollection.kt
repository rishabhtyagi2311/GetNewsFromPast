package chat.first.getnews


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController


@Composable
fun DisplayNews(navController: NavHostController , year : Int , month:Int)
{
    if(year ==0 || month == 0)
    {
        Text("Unable to fetch news articles due to invalid input")
    }
    else{
        val newsModel : MainViewModel = viewModel()
        newsModel.fetchNews(year, month)



        Box(modifier = Modifier.fillMaxSize())
        {
            when
            {
                newsModel.screenState.value.loading->{
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                newsModel.screenState.value.error!=null->{
                    Text("Error Occured")
                }
                else ->
                {
                    Articles(newsModel.screenState.value.list , navController)
                }
            }
        }

    }

}
@Composable
fun Articles(Article : List<Article> , navController: NavHostController)
{
    LazyVerticalGrid(GridCells.Fixed(1), modifier = Modifier.fillMaxSize())
    {
        items(Article)
        {
            news->
            NewsDisplay(news , navController)

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDisplay(news : Article , navController: NavHostController) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue =
        if (expandedState) 180f else 0f
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 100,
                    easing = LinearOutSlowInEasing
                )

            )
            .padding(12.dp)
            .border(2.dp, Color.Cyan, RoundedCornerShape(4.dp)),
        shape = RoundedCornerShape(4),
        onClick = {
            expandedState = !expandedState
        })
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
        {
            Row(verticalAlignment = Alignment.CenterVertically) {


                Text(
                    text = "${news.headline.main}",
                    modifier = Modifier.weight(6f),
                    fontWeight = FontWeight.Bold,
                    maxLines = 3,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(modifier = Modifier
                    .alpha(0.5F)
                    .weight(1f)
                    .rotate(rotationState),

                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )


                }

            }
            if (expandedState) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "${news.lead_paragraph}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "Date of Publish : ${news.pub_date.substring(0,10)}",

                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )
                val annotatedString = buildAnnotatedString {
                    val text = "Read More"

                    val start = text.indexOf("Read")
                    val end = start + text.length
                    append(text)
                    addStyle(
                        SpanStyle(
                            color = Color.Cyan,
                            textDecoration = TextDecoration.Underline
                        ),
                        start = start,
                        end = end
                    )
                    addStringAnnotation(
                        "url",
                        news.web_url,
                        start,
                        end
                        
                    )
                }
                val uriHandler = LocalUriHandler.current
                ClickableText(text = annotatedString,


                    onClick =
                {

                    offset->
                    val uri = annotatedString.getStringAnnotations("url",offset,offset).firstOrNull()?.item
                    if(uri!=null)
                    {
                        uriHandler.openUri(uri)
                    }

                })

            }

        }
    }
}
@Composable
fun HyperLink(text:String , url : String)
{
  Text(text = text ,
      modifier  = Modifier.clickable {

      })
}
