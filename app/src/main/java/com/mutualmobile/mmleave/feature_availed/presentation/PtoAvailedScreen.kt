package com.mutualmobile.mmleave.feature_availed.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mutualmobile.mmleave.R.drawable
import com.mutualmobile.mmleave.common_ui.components.ExpandingText
import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.common_ui.components.toLocalDate
import com.mutualmobile.mmleave.feature_pto.presentation.PtoRequestViewModel
import com.mutualmobile.mmleave.ui.theme.blueTextColorLight
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Preview
@Composable
fun PtoAvailedScreen(
    ptoRequestViewModel: PtoRequestViewModel = hiltViewModel()
) {
    ptoRequestViewModel.getUserPtoLeft()
    val ptoLeft = ptoRequestViewModel.userPtoLeftState.collectAsState()

    LaunchedEffect(key1 = true) {
        ptoRequestViewModel.getAllRemotePtoRequest()
    }

    PtoList(
        ptoRequestViewModel.allPtoSelectedList.value.allPtoRequestRemoteList,
        totalPtoLeft = ptoLeft.value
    )
}

@Composable
fun PtoList(
    ptoList: List<FirebasePtoRequestModel?>,
    totalPtoLeft: Int? = 0
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        item {
            Text(
                text = "$totalPtoLeft of 24 PTOs Availed",
                textAlign = Companion.Start,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
        }

        items(ptoList) { item ->
            Box(
                modifier = Modifier
                    .border(1.dp, color = Color.Gray)
            ) {
                item?.let { pto ->
                    PtoElement(pto = pto)
                }
            }
        }
    }
}

@Composable
fun PtoElement(pto: FirebasePtoRequestModel) {

    val admins = remember {
        mutableListOf<String?>()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp, bottom = 16.dp,
                start = 16.dp, end = 16.dp
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(30.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.White),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Icon(
                    painterResource(id = drawable.calendar_3x),
                    contentDescription = "content description"
                )
            }

            Text(
                text = pto.date.toLocalDate().toString(),
                modifier = Modifier.padding(
                    bottom = 4.dp,
                    start = 4.dp
                )
            )
        }
        ExpandingText(
            text = AnnotatedString(text = pto.description.toString()),
            modifier = Modifier
                .padding(4.dp)
        )

        pto.selectedAdmins?.forEach {
            admins.add(it)
        }

        Text(
            text = "${pto.ptoStatus?.uppercase()} By : $admins",
            modifier = Modifier
                .align(Alignment.End)
                .padding(all = 8.dp),
            style = TextStyle(color = blueTextColorLight)
        )
    }
}
