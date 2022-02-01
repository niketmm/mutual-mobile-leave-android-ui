package com.mutualmobile.mmleave.common_ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser

@ExperimentalCoilApi
@Composable
fun AdminChip(
    mmUser: MMUser,
    isSelected: Boolean = false,
    onSelectionChanged: (MMUser) -> Unit = {},
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) MaterialTheme.colors.primary else  Color.LightGray
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                   onSelectionChanged(mmUser)
                }
            ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImageHolder(
                imageUrl = mmUser.photoUrl.toString()
            )
            mmUser.displayName?.let { name ->
                Text(
                    text = name,
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                    modifier = Modifier.padding(4.dp).wrapContentWidth()
                )
            }
        }
    }
}

@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
fun AdminChipPreview() {
    AdminChip(
        mmUser = MMUser(
            "Niket Jain",
            photoUrl = "https://www.denofgeek.com/wp-content/uploads/2017/10/batman-the-animated-series_0.jpg?resize=768%2C432"
        )
    )
}