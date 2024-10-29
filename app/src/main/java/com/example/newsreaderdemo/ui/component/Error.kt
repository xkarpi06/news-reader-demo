package com.example.newsreaderdemo.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.newsreaderdemo.ui.theme.AppTheme
import com.example.newsreaderdemo.R

@Composable
fun Error(
    modifier: Modifier = Modifier,
    iconSize: Dp = 200.dp
) {
    Box(modifier = modifier) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_error_24),
            contentDescription = "Error",
            modifier = Modifier
                .align(Alignment.Center)
                .size(iconSize),
            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
        )

    }
}

@Preview
@Composable
private fun ComponentPreview() {
    AppTheme {
        Error(modifier = Modifier.size(300.dp))
    }
}
