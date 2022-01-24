package com.mutualmobile.mmleave.ui.screens.pto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mutualmobile.mmleave.R
import com.mutualmobile.mmleave.data.model.PtoRequestCompose
import com.mutualmobile.mmleave.ui.theme.secondaryColorLight
@Preview
@Composable
fun PtoRequestScreen() {

    val text = stringResource(R.string.loreum_ipsum_demo_text)

    PtosRequestList(
        ptosList = listOf(
            PtoRequestCompose(
                "Rebecca Knight", 22,
                "Feb 20, 2021 - Feb 25, 2021", "" +
                        text, "Debbie Reynolds",
                "approved"
            ),
            PtoRequestCompose(
                "Rebecca Knight", 22,
                "Feb 20, 2021 - Feb 25, 2021", "" +
                        text, "Debbie Reynolds",
                "approved"
            ),
            PtoRequestCompose(
                "Rebecca Knight", 22,
                "Feb 20, 2021 - Feb 25, 2021", "" +
                        text, "Debbie Reynolds",
                "approved"
            ),
        )
    )
}

@Composable
fun PtosRequestList(ptosList: List<PtoRequestCompose>) {
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PTO Requests- 06",
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, secondaryColorLight),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue)
                ) {
                    Icon(
                        painterResource(id = R.drawable.calendar_3x),
                        contentDescription = "content description"
                    )
                }
            }

        }
        items(ptosList) { pto ->
            Box(
                modifier = Modifier
                    .border(1.dp, color = Color.Gray)
            ) {
            }
        }
    }
}

