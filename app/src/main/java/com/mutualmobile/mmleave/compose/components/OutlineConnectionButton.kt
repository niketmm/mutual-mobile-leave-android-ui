package com.mutualmobile.mmleave.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConnectedTv
import androidx.compose.material.icons.filled.NetworkWifi
import androidx.compose.material.icons.filled.NotInterested
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.SignalWifiStatusbarConnectedNoInternet4
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mutualmobile.mmleave.R
import com.mutualmobile.mmleave.navigation.Screen
import com.mutualmobile.mmleave.ui.theme.secondaryColorLight

@Composable
fun OutlineConnectionButton(
    isConnected : Boolean
) {
    OutlinedButton(
        onClick = {

        },
        modifier = Modifier.size(35.dp),
        shape = CircleShape,
        border = BorderStroke(1.dp, color = secondaryColorLight),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        if (isConnected)
        Icon(
            imageVector = Icons.Filled.NetworkWifi,
            contentDescription = "content description"
        )
        else
            Icon(
                imageVector = Icons.Filled.WifiOff,
                contentDescription = "content description"
            )
    }
}

@Preview(showBackground = true)
@Composable
fun OutlineConnectionButtonPreview() {
    OutlineConnectionButton(false)
}