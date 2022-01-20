package com.mutualmobile.mmleave.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.mutualmobile.mmleave.util.ConnectionState
import com.mutualmobile.mmleave.util.currentConnectivityState
import com.mutualmobile.mmleave.util.observeConnectivityAsFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    // Creates a State<ConnectionState> with current connectivity state as initial value
    return produceState(initialValue = context.currentConnectivityState) {
        // In a coroutine, can make suspend calls
        context.observeConnectivityAsFlow().collectLatest { it ->
            value = it
        }
    }
}