package com.mutualmobile.mmleave.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mutualmobile.mmleave.R
import com.mutualmobile.mmleave.model.PtoRequest
import com.mutualmobile.mmleave.ui.theme.alertRed
import com.mutualmobile.mmleave.ui.theme.blueTextColorLight
import com.mutualmobile.mmleave.ui.theme.primaryColorLight
import com.mutualmobile.mmleave.ui.theme.secondaryColorLight
import com.mutualmobile.mmleave.ui.theme.purpleTextColorLight

@Preview
@Composable
fun PtoRequestScreen() {
  val text =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
  PtosRequestList(
      ptosList = listOf(
          PtoRequest(
              "Rebecca Knight", 22,
              "Feb 20, 2021 - Feb 25, 2021", "" +
             text, "Debbie Reynolds",
              "approved"
          ),
          PtoRequest(
              "Rebecca Knight", 22,
              "Feb 20, 2021 - Feb 25, 2021", "" +
             text, "Debbie Reynolds",
              "approved"
          ),
          PtoRequest(
              "Rebecca Knight", 22,
              "Feb 20, 2021 - Feb 25, 2021", "" +
             text, "Debbie Reynolds",
              "approved"
          ),
      )
  )
}

@Composable
fun PtosRequestList(ptosList: List<PtoRequest>) {
  LazyColumn(
      contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier
          .fillMaxWidth()
          .padding(start = 8.dp, end = 8.dp)
  ) {
    item {
      Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
            text = "PTO Requests- 06",
            textAlign = TextAlign.Start,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(50.dp),  //avoid the oval shape
            shape = CircleShape,
            border = BorderStroke(1.dp, secondaryColorLight),
            contentPadding = PaddingValues(0.dp),  //avoid the little icon
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue)
        ) {
          Icon(
              painterResource(id = R.drawable.calendar_3x),
              contentDescription = "content description"
          )
        }
      }

    }
    items(ptosList) { pto ->
      Box(
          modifier = Modifier
              .border(1.dp, color = Color.Gray)
      ) {
        PtoRequestElement(pto)
      }
    }
  }
}

@Composable
fun PtoRequestElement(request: PtoRequest) {
  Column(
      modifier = Modifier
          .fillMaxWidth()
          .padding(
              top = 16.dp, bottom = 16.dp,
              start = 16.dp, end = 16.dp
          )
  ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {

      Image(
          painter = painterResource(R.drawable.cristiano_ronaldo_2018),
          contentDescription = "avatar",
          contentScale = ContentScale.Crop,            // crop the image if it's not a square
          modifier = Modifier
              .size(64.dp)
              .clip(CircleShape)                       // clip to the circle shape
      )
      Row(
          modifier = Modifier
              .padding(start = 6.dp)
              .fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
            text = request.name,
            style = TextStyle(color = blueTextColorLight)
        )
        Text(
            text = "Leaves Left: ${request.leavesLeft}",
            style = TextStyle(color = purpleTextColorLight)
        )
      }

    }
    Text(
        text = request.date,
        modifier = Modifier.padding(
            bottom = 4.dp, start = 4.dp, end = 4.dp
        ),
        style = TextStyle(color = primaryColorLight)

    )
    ExpandingText(text = AnnotatedString(text = request.description),
        modifier = Modifier.padding(
            bottom = 16.dp, start = 4.dp, end = 4.dp
        )
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
      Button(
          onClick = { /*TODO*/ },
          colors = ButtonDefaults.buttonColors(
              backgroundColor = primaryColorLight,
              contentColor = Color.White
          ),
          modifier = Modifier.padding(end = 16.dp)
      ) {
        Text(
            text = "APPROVE", modifier = Modifier.padding(end = 4.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.check_3x),
            contentDescription = " cross icon reject button"
        )

      }
      Button(
          onClick = { /*TODO*/ },
          colors = ButtonDefaults.buttonColors(
              backgroundColor = Color.White,
              contentColor = alertRed
          )
      ) {
        Text(
            text = "REJECT", modifier = Modifier.padding(end = 4.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.x_circle_3x),
            contentDescription = " cross icon reject button"
        )
      }
    }
  }
}