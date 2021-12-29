package com.mutualmobile.mmleave.compose.screens.ptoavailed

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mutualmobile.mmleave.model.PtoData

@Preview
@Composable
fun PtoAvailedScreen() {
  PtosList(
      ptosList = listOf(
          PtoData(
              "Feb 20, 2021 - Feb 25, 2021", "" +
              "Far far behind the word mountains Vokalia and... Read more", "Debbie Reynolds"
          ),
          PtoData(
              "Feb 20, 2021 - Feb 25, 2021", "" +
              "Far far behind the word mountains Vokalia and... Read more", "Debbie Reynolds"
          ),
          PtoData(
              "Feb 20, 2021 - Feb 25, 2021", "" +
              "Far far behind the word mountains Vokalia and... Read more", "Debbie Reynolds"
          )
      )
  )
}

@Composable
fun PtosList(ptosList: List<PtoData>) {
  LazyColumn(
      contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier.padding(start = 8.dp, end = 8.dp)
  ) {
    item {
      Text(
          text = "18 of 24 PTOs Availed",
          textAlign = Companion.Start,
          style = TextStyle(
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp
          )
      )
    }
    items(ptosList) { pto ->
      PtoElement(pto)
    }
  }
}

@Composable
fun PtoElement(data: PtoData) {
  Column(
      modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp, bottom = 4.dp)
          .border(1.dp, color = Color.Gray)
  ) {
    Text(
        text = data.date,
        modifier = Modifier.padding(
            bottom = 4.dp
        )
    )
    Text(
        text = data.description,
        modifier = Modifier
            .padding(bottom = 4.dp),
    )
    Text(
        text = "Approved By: " + data.approvedBy,
        modifier = Modifier
            .align(Alignment.End)
            .padding(bottom = 4.dp)
    )
  }
}
