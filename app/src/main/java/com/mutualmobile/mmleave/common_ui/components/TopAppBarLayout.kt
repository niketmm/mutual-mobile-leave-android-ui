package com.mutualmobile.mmleave.common_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mutualmobile.mmleave.ui.theme.white_background

@Composable
fun TopAppBarLayout() {
  TopAppBar(
    backgroundColor = white_background,
      modifier = Modifier.fillMaxWidth(),
      title = {
        Box(modifier = Modifier.fillMaxWidth()) {
          Text(text = "Apply PTO", modifier = Modifier.align(Alignment.Center))
        }
      },
  )
}

@Composable
@Preview
fun PreviewTopAppBar() {
  TopAppBarLayout()
}