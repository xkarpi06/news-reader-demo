package com.example.elongaassignmentapp.ui.screen.article

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.elongaassignmentapp.ui.theme.AppTheme

@Composable
internal fun ArticleScreenLayout() {
    Text(text = "Article Screen")
}

@Preview
@Composable
private fun ComponentPreview() {
    AppTheme {
        ArticleScreenLayout()
    }
}
