package com.mutualmobile.mmleave.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.mutualmobile.mmleave.R
import com.mutualmobile.mmleave.R.string
import com.mutualmobile.mmleave.compose.components.OutlineCalendarButton
import com.mutualmobile.mmleave.firestore.PtoStatus
import com.mutualmobile.mmleave.navigation.Screen
import com.mutualmobile.mmleave.screens.ExpandingText
import com.mutualmobile.mmleave.ui.theme.primaryColorLight
import com.mutualmobile.mmleave.ui.theme.secondaryTextColorDark
import java.time.LocalDate
import java.time.ZoneId

@ExperimentalCoilApi
@Composable
fun HomeScreen(
  navController: NavHostController,
  homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
  val selectedDatesList = homeScreenViewModel.ptoListRequestState
  val toSelectDates= selectedDatesList.value.map {
    it.date?.toDate()?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()!!
  }
  val ptosMap = mutableMapOf<LocalDate, PtoStatus>()
  selectedDatesList.value.map { ptoRequest ->
    ptoRequest.currentStatus?.let { safeStatus ->
      ptosMap.put(
          ptoRequest.date?.toDate()?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()!!,
          safeStatus
      )
    }
  }
  val constraints = ConstraintSet {
    val topProfileAndGreetingLayout = createRefFor("topProfileAndGreetingLayout")
    val topCalendarViewLayout = createRefFor("topCalendarViewLayout")
    val upperMidTotalLeaveLayout = createRefFor("upperMidTotalLeaveLayout")
    val middleLine = createRefFor("middleLine")
    val lowerMidAppliedLeaveLayout = createRefFor("lowerMidAppliedLeaveLayout")
    val lowerFooterLayout = createRefFor("lowerFooterLayout")
    val lowerFooterButton = createRefFor("lowerFooterButton")

    // Todo : Cross Check those guidelines using the Constraint ToolKit.
    val midGuideline = createGuidelineFromTop(0.7f)
    val verticalMidGuideline = createGuidelineFromAbsoluteLeft(0.6f)

    constrain(topProfileAndGreetingLayout) {
      top.linkTo(parent.top)
      start.linkTo(parent.start)
      end.linkTo(parent.end)
    }

    constrain(topCalendarViewLayout) {
      top.linkTo(topProfileAndGreetingLayout.bottom)
      start.linkTo(parent.start)
      end.linkTo(parent.end)
    }

    constrain(upperMidTotalLeaveLayout) {
      start.linkTo(parent.start)
      end.linkTo(parent.end)
      top.linkTo(topCalendarViewLayout.bottom)
      bottom.linkTo(midGuideline)
    }

    constrain(middleLine) {
      start.linkTo(parent.start)
      end.linkTo(parent.end)
      top.linkTo(midGuideline)
    }

    constrain(lowerMidAppliedLeaveLayout) {
      start.linkTo(parent.start)
      end.linkTo(parent.end)
      top.linkTo(middleLine.bottom)
    }

    constrain(lowerFooterLayout) {
      start.linkTo(parent.start)
      bottom.linkTo(parent.bottom)
    }

    constrain(lowerFooterButton) {
      start.linkTo(verticalMidGuideline)
      bottom.linkTo(parent.bottom)
    }

  }

  ConstraintLayout(constraintSet = constraints, modifier = Modifier.fillMaxSize()) {
    Spacer(modifier = Modifier.width(24.dp))
    Row(
        modifier = Modifier
            .layoutId("topProfileAndGreetingLayout")
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

      Column {
        Text(text = "Good Morning", fontSize = 16.sp, color = secondaryTextColorDark)
        Text(text = "Laksh", fontSize = 24.sp)
      }

      OutlineCalendarButton(navController)
      ProfileImageHolder()
    }

    Spacer(modifier = Modifier.width(12.dp))

    Box(
        modifier = Modifier
            .layoutId("topCalendarViewLayout")
            .padding(bottom = 12.dp),
        contentAlignment = Alignment.Center
    ) {
      StaticHomeCalendar(toSelectDates)
    }

    Surface(
        modifier = Modifier
            .layoutId("upperMidTotalLeaveLayout")
            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp)
            .height(158.dp)
            .clickable { navController.navigate(Screen.Splash.route) },
        shape = MaterialTheme.shapes.large,
        color = primaryColorLight,
    ) {
      Row(
          modifier = Modifier
              .fillMaxSize(),
          horizontalArrangement = Arrangement.SpaceEvenly,
          verticalAlignment = Alignment.CenterVertically
      ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(start = 24.dp)
        ) {
          Text(text = "18 of 24", fontSize = 40.sp, color = Color.White)
          Text(text = "PTOs availed", fontSize = 20.sp, color = secondaryTextColorDark)
          Text(text = "SEE DETAILS -->", fontSize = 16.sp, color = Color.White)
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
          LeaveAnimatedCircularProgressBar(
              percentage = 0.77f,
              totalValue = 100
          )
        }
      }
    }

    // Showing a line
    Box(
        modifier = Modifier
            .layoutId("middleLine")
            .fillMaxWidth()
            .padding(24.dp)
            .height(1.dp)
            .background(color = secondaryTextColorDark)
    ) {
      // This is just a view (Line)
    }

    Column(
        modifier = Modifier
            .layoutId("lowerMidAppliedLeaveLayout")
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth()
    ) {
      Text(text = "Applied PTOs", modifier = Modifier.padding(bottom = 16.dp))
      Surface(
          modifier = Modifier
              .clickable { navController.navigate(Screen.PtoRequests.route) }
              .fillMaxWidth()
              .height(120.dp),
          shape = MaterialTheme.shapes.large,
          color = Color.White
      ) {
        Column(modifier = Modifier.fillMaxSize()) {
          Row(
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(start = 4.dp, end = 4.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically,
          ) {
            Text(text = "Feb 20, 2021 - Feb 25, 2021 ", fontSize = 14.sp)
            Text(text = "Approved", fontSize = 18.sp)
          }
          Spacer(modifier = Modifier.height(16.dp))
          ExpandingText(text = AnnotatedString(text = stringResource(id = string.long_text)))
        }
      }
    }

    Box(
        modifier = Modifier
            .layoutId("lowerFooterLayout")
            .fillMaxWidth()
    ) {
      Image(
          painterResource(id = R.drawable.home_page_illus_mm_leave),
          contentDescription = "two_people_illustration",
          modifier = Modifier
              .height(200.dp)
              .width(270.dp),
      )
    }

    Box(
        modifier = Modifier
            .layoutId("lowerFooterButton")
            .padding(bottom = 32.dp)
    ) {
      Button(onClick = { navController.navigate(Screen.ApplyPto.route) }) {
        Text(text = "APPLY PTO")
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "forward arrow for the button"
        )
      }
    }
  }
}

@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
  HomeScreen(navController = rememberNavController())
}

@Composable
fun LeaveAnimatedCircularProgressBar(
  fontSize: TextUnit = 25.sp,
  radius: Dp = 50.dp,
  animationDelay: Int = 0,
  animationDuration: Int = 1500,
  stokeWidth: Dp = 8.dp,
  color: Color = secondaryTextColorDark,
  percentage: Float,
  totalValue: Int
) {

  var animationPlayed by remember {
    mutableStateOf(false)
  }

  val currentPercentage = animateFloatAsState(
      targetValue = if (animationPlayed) percentage else 0F,
      animationSpec = tween(
          durationMillis = animationDuration,
          delayMillis = animationDelay
      )
  )

  LaunchedEffect(key1 = true) {
    animationPlayed = true
  }

  // Box Which holds the Animation and TV
  Box(contentAlignment = Alignment.Center, modifier = Modifier.size(radius * 2)) {
    Canvas(modifier = Modifier.size(radius * 2), onDraw = {
      // Method Provided in the DrawScope by Canvas
      drawArc(
          color = color,
          startAngle = 30f,
          360 * currentPercentage.value,
          false,
          style = Stroke(stokeWidth.toPx(), cap = StrokeCap.Round)
      )
    })
    Text(
        text = (currentPercentage.value * totalValue).toInt().toString(),
        color = color,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold
    )
  }
}

@Composable
fun ExpandableTextLayoutWithReadMoreFeature(
  text: String
) {
  val MINIMUM_LINE_SIZE = 2
  // [TextLayoutResult] provides every details we need for the Expandable feature
  var textLayoutResultState by remember { mutableStateOf<TextLayoutResult?>(null) }
  var finalText by remember { mutableStateOf(text) }

  // Click Events
  var isClickable by remember { mutableStateOf(false) }
  var isExpandable by remember { mutableStateOf(false) }

  // Whenever the Value of the state will be changed the Compose will be recomposed
  LaunchedEffect(textLayoutResultState) {
    if (textLayoutResultState == null) {
      return@LaunchedEffect
    }

    when {
      isExpandable -> {
        finalText = "$text Show Less"
      }

      !isExpandable && textLayoutResultState!!.hasVisualOverflow -> {
        val lastCharIndex = textLayoutResultState!!.getLineEnd(MINIMUM_LINE_SIZE - 1)
        val showMoreString = "... Read More"
        val adjustmentText = text
            .substring(startIndex = 0, endIndex = lastCharIndex)
            .dropLast(showMoreString.length)
            .dropLastWhile {
              it.equals(" ") || it.equals(".")
            }

        finalText = "$adjustmentText $showMoreString"

        isClickable = true
      }
    }
  }

  Text(
      text = finalText,
      maxLines = if (isExpandable) Int.MAX_VALUE else MINIMUM_LINE_SIZE,
      onTextLayout = {
        textLayoutResultState = it
      },
      modifier = Modifier
          .clickable(enabled = isClickable) {
            isExpandable = !isExpandable
          }
          .animateContentSize()

      // Todo add a animation here to make it look smooth
  )
}

@ExperimentalCoilApi
@Composable
fun ProfileImageHolder(
  imageUrl: String = "https://avatars.githubusercontent.com/u/66577?v=4"
) {
  Box(
      modifier = Modifier
          .width(40.dp)
          .height(40.dp),
      contentAlignment = Alignment.Center
  ) {

    val imagePainter = rememberImagePainter(
        data = imageUrl,
        builder = {
          placeholder(R.drawable.mm_splash_logo)
          crossfade(1000)
          transformations(
              CircleCropTransformation()
          )
        }
    )
    // This is to control the State of the Async call of the Coil Image request
    val imagePainterState = imagePainter.state

    // Calling the sealed class from the Coil Lib
    // This is Crashing the app for some reason
//        when(imagePainterState){
//            is ImagePainter.State.Loading -> {
//
//            }
//            is ImagePainter.State.Success -> TODO()
//            is ImagePainter.State.Error -> TODO()
//            ImagePainter.State.Empty -> TODO()
//        }

    // Fetching the Image and populating Image
    Image(painter = imagePainter, contentDescription = "profile image")
  }
}