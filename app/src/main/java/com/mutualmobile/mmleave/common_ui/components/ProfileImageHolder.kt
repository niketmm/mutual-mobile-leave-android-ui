package com.mutualmobile.mmleave.common_ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.R
import com.mutualmobile.mmleave.ui.navigation.Screen

@ExperimentalCoilApi
@Composable
fun ProfileImageHolder(
    navHostController: NavHostController? = null,
    imageUrl: String? = FirebaseAuth.getInstance().currentUser?.photoUrl.toString(),
    size: Dp = 40.dp,
    logoutClickEvent : () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(size = size),
        contentAlignment = Alignment.Center
    ) {
        val imagePainter = rememberImagePainter(
            data = imageUrl,
            builder = {
                placeholder(R.drawable.mm_splash_logo)
                crossfade(500)
                transformations(
                    CircleCropTransformation()
                )
            }
        )
        // This is to control the State of the Async call of the Coil Image request
        val imagePainterState = imagePainter.state

        // Calling the sealed class from the Coil Lib
        // This is Crashing the app for some reason
//        when(imagePainterState){
//            is ImagePainter.State.Loading -> {
//
//            }
//            is ImagePainter.State.Success -> TODO()
//            is ImagePainter.State.Error -> TODO()
//            ImagePainter.State.Empty -> TODO()
//        }

        // Fetching the Image and populating Image
        Image(
            painter = imagePainter,
            contentDescription = "profile image",
            modifier = Modifier.clickable {
                logoutClickEvent()
                navHostController?.popBackStack()
                navHostController?.navigate(Screen.SignUp.route)
            }
        )
    }
}