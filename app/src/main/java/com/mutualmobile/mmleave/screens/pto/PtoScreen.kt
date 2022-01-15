package com.mutualmobile.mmleave.screens.pto

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.compose.components.AdminChip
import com.mutualmobile.mmleave.screens.home.CalendarView
import com.mutualmobile.mmleave.screens.pto.viewmodel.PtoRequestViewModel
import com.mutualmobile.mmleave.screens.search.SearchScreen
import com.mutualmobile.mmleave.services.database.search_user.SearchUserViewModel
import com.mutualmobile.mmleave.ui.theme.backgroundLight
import com.mutualmobile.mmleave.ui.theme.purpleTextColorLight
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@Composable
fun ApplyPtoScreen(
    ptoViewModel: PtoRequestViewModel = hiltViewModel(),
    searchUserViewModel: SearchUserViewModel = hiltViewModel()
) {
    val TAG = "PtoScreen"
    val scrollableState = rememberScrollState()
    var leaveDescriptionText by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (ptoViewModel.isValidPtoRequest(
                            ptoViewModel.allPtoSelectedList.value.localDateList,
                            searchUserViewModel.adminListState.value.selectedAdminList
                        )){
                        ptoViewModel.updatePtoList(
                            ptoList = ptoViewModel.allPtoSelectedList.value.localDateList,
                            email = FirebaseAuth.getInstance().currentUser?.email,
                            desc = leaveDescriptionText,
                            selectedAdmins = searchUserViewModel.adminListState.value.selectedAdminList
                        )
                        ptoViewModel.applyPtoRequest(
                            selectedAdmins = searchUserViewModel.adminListState.value.selectedAdminList
                        )
                    }else{
                        Log.d(TAG, "ApplyPtoScreen: SOme properties are remaining")
                    }
                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add_pto_desc")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollableState)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                SearchScreen(searchUserViewModel)

                Spacer(modifier = Modifier.height(8.dp))

                CalendarView(ptoViewModel)

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp)
                ) {
                    Text(
                        text = "Leaves Left: 18",
                        color = purpleTextColorLight,
                        fontSize = 16.sp, modifier = Modifier
                            .padding(top = 4.dp, bottom = 4.dp)
                    )
                }
                Text(
                    text = "Add Reason Of Leave (Optional)",
                    fontSize = 16.sp, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp)
                )

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = leaveDescriptionText,
                        onValueChange = {
                            leaveDescriptionText = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(color = backgroundLight),
                        maxLines = 5,
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp)
                ) {
                    LazyRow(content = {
                        items(searchUserViewModel.adminListState.value.selectedAdminList) { admin ->
                            admin?.let { it1 -> AdminChip(mmUser = it1) }
                        }
                    })
                }
            }
        }
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@Preview(showBackground = true)
@Composable
fun PtoScreenPreview() {
    ApplyPtoScreen()
}