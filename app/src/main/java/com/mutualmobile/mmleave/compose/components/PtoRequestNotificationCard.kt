package com.mutualmobile.mmleave.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.mutualmobile.mmleave.R
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.data.model.NotificationModel
import com.mutualmobile.mmleave.screens.home.toLocalDate
import com.mutualmobile.mmleave.ui.theme.alertRed
import com.mutualmobile.mmleave.ui.theme.blueTextColorLight
import com.mutualmobile.mmleave.ui.theme.primaryColorLight
import com.mutualmobile.mmleave.ui.theme.purpleTextColorLight

@ExperimentalCoilApi
@Composable
fun PtoRequestNotificationCard(
    mmUser: MMUser? = null,
    notificationModel: NotificationModel?,
    onApprovedClicked: (NotificationModel) -> Unit,
    onRejectedClicked: (NotificationModel) -> Unit,
) {
    val dateTitle = "${notificationModel?.datesList?.first().toLocalDate().toString()} " +
            " - " +
            notificationModel?.datesList?.last().toLocalDate().toString()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(all = 4.dp),
        color = MaterialTheme.colors.surface,
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                // TODO Remove this after testing
                Image(
                    painter = painterResource(id = R.drawable.mm_splash_logo),
                    contentDescription = "dummy_image_holder"
                )
//            ProfileImageHolder(imageUrl = mmUser?.photoUrl)
                Row(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // TODO Remove this after testing
                    Text(
                        text = "Display Name here",
                        style = TextStyle(color = blueTextColorLight)
                    )
                    mmUser?.displayName?.let {
                        Text(
                            text = it,
                            style = TextStyle(color = blueTextColorLight)
                        )
                    }
                    Text(
                        text = "Leaves Left: ${mmUser?.leaveLeft}",
                        style = TextStyle(color = purpleTextColorLight)
                    )
                }

            }
            Text(
                text = dateTitle,
                modifier = Modifier.padding(
                    all = 6.dp
                ),
                style = TextStyle(color = primaryColorLight)
            )

            notificationModel?.title?.let { AnnotatedString(text = it) }?.let {
                ExpandingText(
                    text = it,
                    modifier = Modifier.padding(
                        bottom = 16.dp, start = 4.dp, end = 4.dp
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        notificationModel?.let {
                            onApprovedClicked(it)
                        }
                    },
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
                    onClick = {
                        notificationModel?.let {
                            onRejectedClicked(it)
                        }
                    },
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

                NotificationStateIcon(notifyType = notificationModel?.notify_type)
            }
        }
    }
}