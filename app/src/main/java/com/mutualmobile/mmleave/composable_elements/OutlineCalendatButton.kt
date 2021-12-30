package com.mutualmobile.mmleave.composable_elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mutualmobile.mmleave.R
import com.mutualmobile.mmleave.ui.theme.PitchLight
import com.mutualmobile.mmleave.ui.theme.cyan

@Composable
fun OutlineCalendarButton() {
    OutlinedButton(
        onClick = { /*TODO*/ },
        modifier = Modifier.size(30.dp),
        shape = CircleShape,
        border = BorderStroke(1.dp, color = cyan),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        Icon(
            painterResource(id = R.drawable.calendar_medium),
            contentDescription = "content description"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OutlineCalendarPreview() {
    OutlineCalendarButton()
}