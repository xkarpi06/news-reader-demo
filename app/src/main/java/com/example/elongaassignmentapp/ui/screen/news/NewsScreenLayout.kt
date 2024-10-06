package com.example.elongaassignmentapp.ui.screen.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.elongaassignmentapp.ui.preview.PreviewData
import com.example.elongaassignmentapp.ui.screen.news.component.NewsItem
import com.example.elongaassignmentapp.ui.screen.news.model.NewsUIState
import com.example.elongaassignmentapp.ui.theme.AppTheme

@Composable
fun NewsScreenLayout(
    uiState: NewsUIState,
    onArticleClick: (articleId: String) -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isRefreshing) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        when (uiState) {
            is NewsUIState.Idle -> {}

            is NewsUIState.Success -> {
                LazyColumn {
                    items(uiState.news) { article ->
                        NewsItem(
                            article = article,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onArticleClick(article.articleId) }
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 126.dp, end = 10.dp)
                        )
                    }
                }
            }

            is NewsUIState.Error -> {
                Text(text = "Failed to load news")
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
