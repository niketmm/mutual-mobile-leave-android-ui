package com.mutualmobile.mmleave.common_ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.dp
import com.mutualmobile.mmleave.ui.theme.secondaryTextColorDark
import com.mutualmobile.mmleave.util.Constants.MINIMIZED_MAX_LINES

@Composable
fun ExpandingText(
    modifier : Modifier = Modifier
        .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
    text: AnnotatedString
) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var finalText by remember {
        mutableStateOf(text)
    }
    Text(
        text = finalText,
        maxLines = if (isExpanded) Int.MAX_VALUE else MINIMIZED_MAX_LINES,
        onTextLayout = { textLayoutResultState.value = it },
        modifier = modifier
            .clickable(enabled = isClickable) { isExpanded = !isExpanded }
            .animateContentSize(),
    )
    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
                val showLessString = ".. Show Less "
                finalText = AnnotatedString(
                    text = text.toString(),
                    spanStyle = SpanStyle(color = secondaryTextColorDark)
                ).plus(
                    AnnotatedString(
                        text = showLessString,
                        spanStyle = SpanStyle(color = Color.Black)
                    )
                )
            }
            !isExpanded && textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(MINIMIZED_MAX_LINES - 1)
                val showMoreString = " ... Show More "
                val adjustedText = text
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length)
                    .dropLastWhile { it == ' ' || it == '.' }

                finalText = AnnotatedString(
                    text = adjustedText,
                    spanStyle = SpanStyle(color = secondaryTextColorDark)
                ).plus(
                    AnnotatedString(
                        text = showMoreString,
                        spanStyle = SpanStyle(color = Color.Black)
                    )
                )
                isClickable = true
            }
        }
    }
}