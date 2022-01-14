package com.mutualmobile.mmleave.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mutualmobile.mmleave.util.CalendarDetailList
import com.mutualmobile.mmleave.util.CalendarDetailUtil

@Composable
fun CalendarDetailsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            RowBoxItemForCalendar(rowList = CalendarDetailList.firstRowList)
            RowBoxItemForCalendar(rowList = CalendarDetailList.secondRowList)
        }
    }
}

@Composable
fun RowBoxItemForCalendar(rowList: List<CalendarDetailUtil>) {
    LazyRow() {
        items(rowList) { item ->
            Row(
                modifier = Modifier.wrapContentWidth()
                    .padding(start = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp)
                        .background(item.boxColour),
                    contentAlignment = Alignment.Center,
                ) {

                }

                Text(text = item.title, fontSize = 16.sp)
            }
        }
    }
}

