package com.nexvora.ai.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nexvora.ai.R

@Composable
fun NexVoraLogo(
    size: Dp = 48.dp,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_nexvora_logo),
        contentDescription = "NexVora AI Logo",
        modifier = modifier.size(size)
    )
}
