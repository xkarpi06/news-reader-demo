package com.example.elongaassignmentapp.ui.screen.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.elongaassignmentapp.ui.preview.PreviewData
import com.example.elongaassignmentapp.ui.screen.news.model.NewsUIState
import com.example.elongaassignmentapp.ui.theme.AppTheme

@Composable
fun NewsScreenLayout(
    uiState: NewsUIState,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isRefreshing) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        Column {
            Text(text = "News Screen")
            when (uiState) {
                is NewsUIState.Idle -> {}

                is NewsUIState.Success -> {
                    uiState.news.forEach { news ->
                        Text(text = news.title ?: "")
                    }
                }

                is NewsUIState.Error -> {
                    Text(text = "Failed to load news")
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    AppTheme {
        NewsScreenLayout(
            uiState = NewsUIState.Idle(true),
        )
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    AppTheme {
        NewsScreenLayout(
            uiState = NewsUIState.Error(),
        )
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    AppTheme {
        NewsScreenLayout(
            uiState = NewsUIState.Success(PreviewData.News.few),
        )
    }
}
