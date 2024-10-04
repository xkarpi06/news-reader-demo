package com.example.elongaassignmentapp.ui.screen.news

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.elongaassignmentapp.ui.theme.AppTheme

@Composable
internal fun NewsScreenLayout() {
    Text(text = "News Screen")
}

@Preview
@Composable
private fun ComponentPreview() {
    AppTheme {
        NewsScreenLayout()
    }
}
