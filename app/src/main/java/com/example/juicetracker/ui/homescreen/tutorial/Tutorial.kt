package com.example.juicetracker.ui.homescreen.tutorial

import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.juicetracker.R
import kotlin.math.absoluteValue


@Composable
fun Tutorial(controller: NavHostController) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
//                horizontalArrangement = Arrangement.Center
            ) {
                Column {
                    Row {
                        Button(
                            onClick = {
                                val startTime = System.currentTimeMillis()
                                controller.navigate("mainApp")
                                val endTime = System.currentTimeMillis()
                                Log.d(
                                    "NavigationTiming",
                                    "Navigation took ${endTime - startTime} ms"
                                )
                            },
                            content = { Text("Skip") },
                            modifier = Modifier,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        )
                    }
                }

            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier,
                snapPosition = pagerState.layoutInfo.snapPosition,
                contentPadding = PaddingValues(start = 50.dp),
                pageSpacing = 20.dp
            ) { page ->
                val pagerOffset = pagerState.getOffsetDistanceInPages(page).absoluteValue
                val tutorialPictures = listOf(
                    R.drawable.tutorial_1,
                    R.drawable.tutorial_2,
                    R.drawable.tutorial_3,
                )
                val tutorialText = listOf(
                    "Tap \"Add Product\"",
                    "jhjkhajdkahdjkah",
                    "akshdgajgda"
                )
                val image = painterResource(tutorialPictures[page])

                Column {
                    Image(
                        painter = image,
                        contentDescription = null,
                        alignment = Alignment.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        modifier = Modifier
                            .background(color = Color.Red, shape = CircleShape)
                            .align(Alignment.CenterHorizontally),
                        text = " Step ${page + 1} ",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = tutorialText[page], textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewThing() {
    Tutorial(rememberNavController())
}