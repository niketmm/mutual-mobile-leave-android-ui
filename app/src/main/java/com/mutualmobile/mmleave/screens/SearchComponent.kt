package com.mutualmobile.mmleave.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.firestore.Designation
import com.mutualmobile.mmleave.model.User
import com.mutualmobile.mmleave.services.database.ptorequest.viewmodel.PtoRequestViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun SearchScreen(
    viewModel: PtoRequestViewModel = hiltViewModel()
) {
    // This contains the Search View and the Lazy Column with the details
    val textState = remember { mutableStateOf(TextFieldValue("Search Admins by here")) }
    val adminList = viewModel.adminUserList
    var filteredList: List<MMUser>

    val searchedText = textState.value.text
    Column(modifier = Modifier.padding(8.dp)) {
        SearchViewComposable(state = textState, viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            filteredList = if (searchedText.isEmpty() && searchedText == "Search Admins by here") {
                adminList
            } else {
                viewModel.adminFilterList
            }

            items(filteredList) { adminList ->
                ColumnCardViewComposable(firebaseAdminUser = adminList)
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun SearchViewComposable(
    state: MutableState<TextFieldValue>,
    viewModel: PtoRequestViewModel
) {
    // Creating a Search View with Text Here
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
            // Checking the state of the Text
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
fun ColumnCardViewComposable(firebaseAdminUser: MMUser) {
    // This will display data in the card
    // Todo Change the Parameter later
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            ProfileImageHolder(
                rememberNavController(),
                firebaseAdminUser.photoUrl
            )

            Text(
                text = firebaseAdminUser.displayName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
fun PreviewSearchComposable() {
    SearchScreen()
}