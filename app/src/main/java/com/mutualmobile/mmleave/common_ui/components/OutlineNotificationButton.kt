package com.mutualmobile.mmleave.common_ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mutualmobile.mmleave.ui.navigation.Screen
import com.mutualmobile.mmleave.ui.theme.secondaryColorLight

@Composable
fun OutlineNotificationButton(
    navController : NavHostController
) {
    OutlinedButton(
        onClick = { navController.navigate(Screen.NotificationScreen.route) },
        modifier = Modifier.size(35.dp),
        shape = CircleShape,
        border = BorderStroke(1.dp, color = secondaryColorLight),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        Icon(
            imageVector = Icons.Filled.Notifications,
            contentDescription = "content description"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OutlineNotificationButtonPreview() {
    OutlineCalendarButton(navController = rememberNavController())
}