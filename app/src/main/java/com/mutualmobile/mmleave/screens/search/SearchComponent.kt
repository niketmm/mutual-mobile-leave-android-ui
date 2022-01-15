package com.mutualmobile.mmleave.screens.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.screens.home.ProfileImageHolder
import com.mutualmobile.mmleave.services.database.search_user.SearchUserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun SearchScreen(
    viewModel: SearchUserViewModel = hiltViewModel()
) {
    val textState = remember { mutableStateOf(TextFieldValue("Search Admins by here")) }

    val adminListState = viewModel.adminListState.value

    var filteredList: List<MMUser?>

    val searchedText = textState.value.text

    val TAG = "Testing"

    Column(modifier = Modifier.padding(4.dp)) {
        SearchViewComposable(state = textState, viewModel)
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            filteredList = if (searchedText.isEmpty() || searchedText == "Search Admins by here") {
                adminListState.adminList
            } else {
                adminListState.filteredList
            }

            items(filteredList) { admin ->
                Row(
                    modifier = Modifier
                        .clickable {
                            val selectedUser = filteredList[filteredList.indexOf(admin)]
                            selectedUser?.copy(isSelected = selectedUser.isSelected?.not())
                            Log.d(TAG, "SearchScreen: ${selectedUser?.isSelected}")
                        }
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ColumnCardViewComposable(firebaseAdminUser = admin)
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun SearchViewComposable(
    state: MutableState<TextFieldValue>,
    viewModel: SearchUserViewModel
) {
    TextField(
        value = state.value,
        onValueChange = { newValue ->
            state.value = newValue
            viewModel.getFilteredAdminUserList(newValue.text)
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 18.sp
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search_icon",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(onClick = {
                    state.value = TextFieldValue("")
                }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close_icon",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@ExperimentalCoilApi
@Composable
fun ColumnCardViewComposable(firebaseAdminUser: MMUser?) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .wrapContentWidth()
            .padding(4.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            firebaseAdminUser?.photoUrl?.let {
                ProfileImageHolder(
                    rememberNavController(),
                    it
                )
            }

            Column(modifier = Modifier.padding(start = 8.dp)) {
                firebaseAdminUser?.let {
                    Text(
                        text = it.displayName ?: "Empty",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }

                firebaseAdminUser?.let {
                    Text(
                        text = it.designation ?: "Empty",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp),
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}