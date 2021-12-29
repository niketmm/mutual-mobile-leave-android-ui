package com.mutualmobile.mmleave.compose.screens.ptorequest

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mutualmobile.mmleave.R.drawable
import com.mutualmobile.mmleave.model.PtoRequest
import com.mutualmobile.mmleave.ui.theme.alert
import com.mutualmobile.mmleave.ui.theme.name_pto_request
import com.mutualmobile.mmleave.ui.theme.primary_1
import com.mutualmobile.mmleave.ui.theme.secondary_accent_1
import com.mutualmobile.mmleave.ui.theme.white_two

@Preview
@Composable
fun PtoRequestScreen() {
  PtosRequestList(
      ptosList = listOf(
          PtoRequest(
              "Rebecca Knight", 22,
              "Feb 20, 2021 - Feb 25, 2021", "" +
              "Far far behind the word mountains Vokalia and... Read more", "Debbie Reynolds",
              "approved"
          ),
          PtoRequest(
              "Rebecca Knight", 22,
              "Feb 20, 2021 - Feb 25, 2021", "" +
              "Far far behind the word mountains Vokalia and... Read more", "Debbie Reynolds",
              "approved"
          ),
          PtoRequest(
              "Rebecca Knight", 22,
              "Feb 20, 2021 - Feb 25, 2021", "" +
              "Far far behind the word mountains Vokalia and... Read more", "Debbie Reynolds",
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
          horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
            text = "PTO Requests- 06",
            textAlign = TextAlign.Start,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        Icon(
            painter = painterResource(id = drawable.calendar_3x),
            contentDescription = null
        )
      }

    }
    items(ptosList) { pto ->
      PtoRequestElement(pto)
    }
  }
}

@Composable
fun PtoRequestElement(request: PtoRequest) {
  Column(
      modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp, bottom = 4.dp)
          .border(1.dp, color = Color.Gray)
  ) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
          text = request.name,
          style = TextStyle(color = name_pto_request)
      )
      Text(
          text = "Leaves Left: ${request.leavesLeft}",
          style = TextStyle(color = secondary_accent_1)
      )
    }
    Text(
        text = request.date,
        modifier = Modifier.padding(
            bottom = 4.dp, start = 4.dp, end = 4.dp
        )
    )
    Text(
        text = request.description,
        modifier = Modifier.padding(
            bottom = 4.dp, start = 4.dp, end = 4.dp
        )
    )
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      Button(
          onClick = { /*TODO*/ },
          colors = ButtonDefaults.buttonColors(
              backgroundColor = primary_1,
              contentColor = white_two
          )
      ) {
        Text(
            text = "APPROVE",
        )
      }
      Button(
          onClick = { /*TODO*/ },
          colors = ButtonDefaults.buttonColors(
              backgroundColor = white_two,
              contentColor = alert
          )
      ) {
        Text(
            text = "REJECT",
        )
      }
    }
  }
}