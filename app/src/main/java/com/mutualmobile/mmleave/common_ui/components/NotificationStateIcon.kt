package com.mutualmobile.mmleave.common_ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NotificationStateIcon(
    notifyType : Int?
) {
    Surface(
        modifier = Modifier.padding(all = 4.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(50),
        color = Color.Gray
    ) {
        // Icons which will show the Status
        when(notifyType){
            1 -> {
                Icon(imageVector =  Icons.Filled.Check, contentDescription = "approved_check", tint = Color.Green)
            }
            2 -> {
                Icon(imageVector =  Icons.Filled.Cancel, contentDescription = "rejected_cross", tint = Color.Red)
            }
            else -> {
                Icon(imageVector =  Icons.Filled.Circle, contentDescription = "applied_circle", tint = Color.Red)
            }
        }
    }

}