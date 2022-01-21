package com.mutualmobile.mmleave.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomePtoAvailedChip(
    currentStatus : String? = null
) {
    var statusText by remember { mutableStateOf("") }
    var isSelected by remember {
        mutableStateOf(false)
    }

    when(currentStatus){
       "APPLIED" -> {
           isSelected = false
           statusText = "Pending"
       }
       "APPROVED" -> {
           isSelected = true
           statusText = "Approved"
       }
       "REJECTED" -> {
           isSelected = false
           statusText = "Rejected"
       }
    }

    Surface(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) MaterialTheme.colors.primary else  Color.LightGray
    ){
        Text(text = statusText, fontSize = 16.sp, modifier = Modifier.padding(all = 8.dp),color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun HomePtoAvailedChipPreview() {
    HomePtoAvailedChip()
}